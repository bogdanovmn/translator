package com.github.bogdanovmn.translator.web.app.admin.upload;

import com.github.bogdanovmn.translator.core.EnglishText;
import com.github.bogdanovmn.translator.core.TranslateServiceUploadDuplicateException;
import com.github.bogdanovmn.translator.parser.pdf.PdfContent;
import com.github.bogdanovmn.translator.web.orm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UploadBookService {
	private static final Logger LOG = LoggerFactory.getLogger(UploadBookService.class);

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

		String fileMd5 = DigestUtils.md5DigestAsHex(file.getBytes());
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

		LOG.info("Parse PDF file");
		PdfContent content = new PdfContent(
			file.getInputStream()
		);

		EnglishText text = new EnglishText(
			content.getText()
		);
		LOG.info("Prepare words");

		Collection<String> words = text.normalizedWords();

		source = this.sourceRepository.save(
			new Source()
				.setRawName(file.getOriginalFilename())
				.setContentHash(fileMd5)
				.setType(SourceType.BOOK)
				.setTitle(content.getTitle())
				.setAuthor(content.getAuthor())
				.setWordsCount(words.size())
		);
		try {
			content.close();
		}
		catch (Exception e) {
			throw new RuntimeException("Close PDF error", e);
		}

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
				this.wordRepository.save(word);
				LOG.info("New word: {}", wordStr);
				newWordsCount++;
			}

			this.wordSourceRepository.save(
				new WordSource()
					.setSource(source)
					.setWord(word)
					.setCount(
						text.getWordFormsFrequance(wordStr)
					)
			);
		}

		LOG.info("Import words done. New words: {}", newWordsCount);

		wordRepository.updateStatistic();
		LOG.info("Update words statistic done");

		return source;
	}
}
