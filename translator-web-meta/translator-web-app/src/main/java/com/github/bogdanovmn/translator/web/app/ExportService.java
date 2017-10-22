package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.web.orm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExportService {
	@Autowired
	private UserRememberedWordRepository userRememberedWordRepository;
	@Autowired
	private WordRepository wordRepository;
	@Autowired
	private EntityFactory entityFactory;
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

	public Map<String, Object> importFromFile(InputStream inputStream)
		throws JAXBException
	{
		Unmarshaller marshaller = JAXBContext.newInstance(ExportSchema.class)
			.createUnmarshaller();

		ExportSchema exportSchema = (ExportSchema) marshaller.unmarshal(inputStream);
		List<Source> sources = exportSchema.getSources();

		return new HashMap<String, Object>() {{
			put("sources", sources);
		}};
	}
}
