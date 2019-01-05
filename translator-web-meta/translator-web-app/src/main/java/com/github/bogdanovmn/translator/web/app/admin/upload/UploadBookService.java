package com.github.bogdanovmn.translator.web.app.admin.upload;

import com.github.bogdanovmn.translator.core.EnglishText;
import com.github.bogdanovmn.translator.core.TranslateServiceUploadDuplicateException;
import com.github.bogdanovmn.translator.parser.common.DocumentContent;
import com.github.bogdanovmn.translator.web.orm.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UploadBookService {
	private final WordRepository wordRepository;
	private final SourceRepository sourceRepository;
	private final WordSourceRepository wordSourceRepository;

	@Autowired
	public UploadBookService(WordRepository wordRepository, SourceRepository sourceRepository, WordSourceRepository wordSourceRepository) {
		this.wordRepository = wordRepository;
		this.sourceRepository = sourceRepository;
		this.wordSourceRepository = wordSourceRepository;
	}

	@Transactional(rollbackFor = Exception.class)
	public synchronized Source upload(MultipartFile file)
		throws IOException, TranslateServiceUploadDuplicateException
	{
		LOG.info("Import data from file");

		byte[] fileBytes = file.getBytes();
		String fileMd5 = DigestUtils.md5DigestAsHex(fileBytes);
		Source source = this.sourceRepository.findFirstByContentHash(fileMd5);
		if (source != null) {
			throw new TranslateServiceUploadDuplicateException(
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

		EnglishText englishText = new EnglishText(fileContent.text());
		LOG.info("Prepare words");

		Collection<String> words = englishText.normalizedWords();

		source = this.sourceRepository.save(
			new Source()
				.setRawName(file.getOriginalFilename())
				.setContentHash(fileMd5)
				.setType(SourceType.BOOK)
				.setTitle(fileContent.title())
				.setAuthor(fileContent.author())
				.setWordsCount(words.size())
		);

		LOG.info("Load exists words");
		Map<String, Word> wordsMap = this.wordRepository.findAll().stream()
			.collect(Collectors.toMap(
				Word::getName, x -> x
			));

		LOG.info("Import words: {}", words.size());

		int newWordsCount = 0;
		for (String wordStr : words) {
			Word word = wordsMap.get(wordStr);
			if (null == word) {
				word = new Word(wordStr);
				wordRepository.save(word);
				LOG.info("New word: {}", wordStr);
				newWordsCount++;
			}

			wordSourceRepository.save(
				new WordSource()
					.setSource(source)
					.setWord(word)
					.setCount(
						englishText.getWordFormsFrequance(wordStr)
					)
			);
		}

		LOG.info("Import words done. New words: {}", newWordsCount);

		wordRepository.updateStatistic();
		LOG.info("Update words statistic done");

		return source;
	}
}
