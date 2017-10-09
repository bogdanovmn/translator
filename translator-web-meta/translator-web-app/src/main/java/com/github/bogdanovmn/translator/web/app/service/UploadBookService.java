package com.github.bogdanovmn.translator.web.app.service;

import com.github.bogdanovmn.translator.core.EnglishText;
import com.github.bogdanovmn.translator.core.exception.TranslateServiceUploadDuplicateException;
import com.github.bogdanovmn.translator.parser.pdf.PdfInputStreamContent;
import com.github.bogdanovmn.translator.web.orm.entity.domain.Source;
import com.github.bogdanovmn.translator.web.orm.entity.domain.SourceType;
import com.github.bogdanovmn.translator.web.orm.entity.domain.Word;
import com.github.bogdanovmn.translator.web.orm.entity.domain.WordSource;
import com.github.bogdanovmn.translator.web.orm.factory.EntityFactory;
import com.github.bogdanovmn.translator.web.orm.repository.domain.SourceRepository;
import com.github.bogdanovmn.translator.web.orm.repository.domain.WordSourceRepository;
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
	private SourceRepository sourceRepository;
	@Autowired
	private WordSourceRepository wordSourceRepository;
	@Autowired
	private EntityFactory entityFactory;

	@Transactional
	public void process(MultipartFile file)
		throws IOException, TranslateServiceUploadDuplicateException
	{
		String fileMd5 = DigestUtils.md5DigestAsHex(file.getBytes());
		Source source = this.sourceRepository.findFirstByContentHash(fileMd5);
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

		source = this.sourceRepository.save(
			new Source()
				.setRawName(file.getOriginalFilename())
				.setContentHash(fileMd5)
				.setType(SourceType.BOOK)
				.setTitle(content.getTitle())
				.setAuthor(content.getAuthor())
				.setWordsCount(text.words().size())
		);

		for (String wordStr : text.words()) {
			Word word = (Word) this.entityFactory.getPersistBaseEntityWithUniqueName(
				new Word(wordStr)
			);

			this.wordSourceRepository.save(
				new WordSource()
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
