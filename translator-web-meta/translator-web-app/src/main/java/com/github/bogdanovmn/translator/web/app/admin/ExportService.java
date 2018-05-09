package com.github.bogdanovmn.translator.web.app.admin;

import com.github.bogdanovmn.translator.web.orm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.OutputStream;

@Service
public class ExportService {
	@Autowired
	private WordRepository wordRepository;
	@Autowired
	private TranslateRepository translateRepository;
	@Autowired
	private TranslateProviderRepository translateProviderRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SourceRepository sourceRepository;
	@Autowired
	private WordSourceRepository wordSourceRepository;

	public void export(OutputStream outputStream)
		throws JAXBException
	{
		Marshaller marshaller = JAXBContext.newInstance(ExportSchema.class)
			.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

		marshaller.marshal(
			new ExportSchema()
				.setSources(
					this.sourceRepository.findAll()
				)
				.setTranslateProviders(
					this.translateProviderRepository.findAll()
				)
				.setWords(
					this.wordRepository.findAll()
				)
				.setTranslates(
					this.translateRepository.findAll()
				)
				.setWordSources(
					this.wordSourceRepository.findAll()
				)
				.setUsers(
					this.userRepository.findAll()
				),
			outputStream
		);
	}
}
