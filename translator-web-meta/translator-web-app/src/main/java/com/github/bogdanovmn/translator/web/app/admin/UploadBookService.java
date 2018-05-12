package com.github.bogdanovmn.translator.web.app.admin;

import com.github.bogdanovmn.translator.core.EnglishText;
import com.github.bogdanovmn.translator.core.TranslateServiceUploadDuplicateException;
import com.github.bogdanovmn.translator.parser.pdf.PdfInputStreamContent;
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

@Service
public class UploadBookService {
	private static final Logger LOG = LoggerFactory.getLogger(UploadBookService.class);

	@Autowired
	private WordRepository wordRepository;
	@Autowired
	private SourceRepository sourceRepository;
	@Autowired
	private WordSourceRepository wordSourceRepository;

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
		PdfInputStreamContent content = new PdfInputStreamContent(
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

		LOG.info("Import words: {}", words.size());

		int newWordsCount = 0;
		for (String wordStr : words) {
			Word word = this.wordRepository.findFirstByName(wordStr);
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
