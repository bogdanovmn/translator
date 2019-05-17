package com.github.bogdanovmn.translator.web.app.admin.upload;

import com.github.bogdanovmn.translator.core.text.EnglishText;
import com.github.bogdanovmn.translator.core.text.ProperNames;
import com.github.bogdanovmn.translator.parser.common.DocumentContent;
import com.github.bogdanovmn.translator.web.orm.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
class UploadBookService {
	@Autowired
	private WordRepository wordRepository;
	@Autowired
	private SourceRepository sourceRepository;
	@Autowired
	private WordSourceRepository wordSourceRepository;
	@Autowired
	private ProperNameRepository properNameRepository;
	@Autowired
	private ProperNameSourceRepository properNameSourceRepository;

	@Transactional(rollbackFor = Exception.class)
	public synchronized Source upload(MultipartFile file)
		throws IOException, UploadDuplicateException
	{
		LOG.info("Import data from file");

		byte[] fileBytes = file.getBytes();
		String fileMd5 = DigestUtils.md5DigestAsHex(fileBytes);
		Source source = this.sourceRepository.findFirstByContentHash(fileMd5);
		if (source != null) {
			throw new UploadDuplicateException(
				String.format(
					"Такая книга уже загружена: %s (md5: %s)",
						source.getName(),
						source.getContentHash()
				)
			);
		}

		LOG.info("Parse file");
		DocumentContent fileContent = DocumentContent.fromInputStream(new ByteArrayInputStream(fileBytes));
		LOG.info("File type: {}", fileContent.contentType());

		EnglishText englishText = EnglishText.fromText(fileContent.text());
		LOG.info("Prepare words");

		Collection<String> words = englishText.normalizedWords();

		source = sourceRepository.save(
			new Source()
				.setRawName(file.getOriginalFilename())
				.setContentHash(fileMd5)
				.setType(SourceType.BOOK)
				.setTitle(fileContent.title())
				.setAuthor(fileContent.author())
				.setWordsCount(words.size())
		);

		LOG.info("Load exists words");
		Map<String, Word> wordsMap = wordRepository.findAll().stream()
			.collect(Collectors.toMap(
				Word::getName, x -> x
			));

		LOG.info("Import words: {}", words.size());

		int newWordsCount = 0;
		for (String wordStr : words) {
			Word word = wordsMap.get(wordStr);
			if (null == word) {
				word = new Word(wordStr);
				LOG.info("New word: {}", wordStr);
				wordRepository.save(word);
				newWordsCount++;
			}

			wordSourceRepository.save(
				new WordSource()
					.setSource(source)
					.setWord(word)
					.setCount(
						englishText.wordFrequency(wordStr)
					)
			);
		}

		LOG.info("Import words done. New words: {}", newWordsCount);

		wordRepository.updateStatistic();
		LOG.info("Update words statistic done");

		LOG.info("Import proper names");
		importProperNames(source, englishText.properNames());

		return source;
	}

	private void importProperNames(Source source, ProperNames properNames) {
		LOG.info("Load exists proper names");
		Map<String, ProperName> existsProperNames = properNameRepository.findAll().stream()
			.collect(Collectors.toMap(
				ProperName::getName, x -> x
			));
		LOG.info("{} proper names in DB total", existsProperNames.size());

		properNames.names().forEach(
			name -> {
				ProperName properName = existsProperNames.get(name);
				if (properName == null) {
					String capitalizedName = StringUtils.capitalize(name);
					LOG.info("New proper name: {}", capitalizedName);
					properName = properNameRepository.save(
						new ProperName(capitalizedName)
					);
				}
				properNameSourceRepository.save(
					new ProperNameSource()
						.setProperName(properName)
						.setCount(properNames.frequency(name))
						.setSource(source)
				);
			}
		);
	}
}
