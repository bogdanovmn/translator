package com.github.bogdanovmn.translator.web.app.service;

import com.github.bogdanovmn.translator.core.EnglishText;
import com.github.bogdanovmn.translator.core.exception.TranslateServiceUploadDuplicateException;
import com.github.bogdanovmn.translator.parser.pdf.PdfInputStreamContent;
import com.github.bogdanovmn.translator.web.orm.entity.domain.Word;
import com.github.bogdanovmn.translator.web.orm.entity.domain.WordSource;
import com.github.bogdanovmn.translator.web.orm.entity.domain.WordSourceStatistic;
import com.github.bogdanovmn.translator.web.orm.entity.domain.WordSourceType;
import com.github.bogdanovmn.translator.web.orm.factory.EntityFactory;
import com.github.bogdanovmn.translator.web.orm.repository.domain.WordRepository;
import com.github.bogdanovmn.translator.web.orm.repository.domain.WordSourceRepository;
import com.github.bogdanovmn.translator.web.orm.repository.domain.WordSourceStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UploadBookService {
	@Autowired
	private WordSourceRepository wordSourceRepository;
	@Autowired
	private WordRepository wordRepository;
	@Autowired
	private WordSourceStatisticRepository wordSourceStatisticRepository;
	@Autowired
	private EntityFactory entityFactory;

	@Transactional
	public void process(MultipartFile file)
		throws IOException, TranslateServiceUploadDuplicateException
	{
		String fileMd5 = DigestUtils.md5DigestAsHex(file.getBytes());
		WordSource source = this.wordSourceRepository.findFirstByContentHash(fileMd5);
		if (source != null) {
			throw new TranslateServiceUploadDuplicateException(
				String.format(
					"Content already exists: %s - %s (md5: %s)",
						source.getAuthor(),
						source.getTitle(),
						source.getContentHash()
				)
			);
		}

		PdfInputStreamContent content = new PdfInputStreamContent(
			file.getInputStream()
		);

		EnglishText text = new EnglishText(
			content.getText()
		);

		source = this.wordSourceRepository.save(
			new WordSource()
				.setRawName(file.getOriginalFilename())
				.setContentHash(fileMd5)
				.setType(WordSourceType.BOOK)
				.setTitle(content.getTitle())
				.setAuthor(content.getAuthor())
		);

		for (String wordStr : text.words()) {
			Word word = (Word) this.entityFactory.getPersistBaseEntityWithUniqueName(
				new Word()
					.addSource(source)
					.setName(wordStr)
			);
			this.wordRepository.save(word);

			this.wordSourceStatisticRepository.save(
				new WordSourceStatistic()
					.setSource(source)
					.setWord(word)
					.setCount(
						text.getWordFrequance(wordStr)
					)
			);
		}

	}

	public List<Object> getHistory() {
		return null;
	}
}
