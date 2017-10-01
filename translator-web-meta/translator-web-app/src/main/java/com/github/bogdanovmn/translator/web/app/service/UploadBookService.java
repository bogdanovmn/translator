package com.github.bogdanovmn.translator.web.app.service;

import com.github.bogdanovmn.translator.core.EnglishText;
import com.github.bogdanovmn.translator.parser.pdf.PdfInputStreamTextContent;
import com.github.bogdanovmn.translator.web.orm.repository.domain.WordRepository;
import com.github.bogdanovmn.translator.web.orm.repository.domain.WordSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UploadBookService {
	@Autowired
	private WordSourceRepository wordSourceRepository;
	@Autowired
	private WordRepository wordRepository;

	public void process(MultipartFile file) throws IOException {
		new EnglishText(
			new PdfInputStreamTextContent(
				file.getInputStream()
			).getText()
		).printStatistic();
	}

	public List<Object> getHistory() {
		return null;
	}
}
