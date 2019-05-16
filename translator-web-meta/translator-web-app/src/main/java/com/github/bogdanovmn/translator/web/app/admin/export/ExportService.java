package com.github.bogdanovmn.translator.web.app.admin.export;

import com.github.bogdanovmn.translator.web.orm.entity.SourceRepository;
import com.github.bogdanovmn.translator.web.orm.entity.UserRepository;
import com.github.bogdanovmn.translator.web.orm.entity.WordRepository;
import com.github.bogdanovmn.translator.web.orm.entity.WordSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.OutputStream;

@Service
class ExportService {
	@Autowired
	private WordRepository wordRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SourceRepository sourceRepository;
	@Autowired
	private WordSourceRepository wordSourceRepository;

	void export(OutputStream outputStream)
		throws JAXBException
	{
		Marshaller marshaller = JAXBContext.newInstance(ExportSchema.class)
			.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

		marshaller.marshal(
			new ExportSchema()
				.setSources(
					sourceRepository.findAll()
				)
				.setWords(
					wordRepository.findAll()
				)
				.setWordSources(
					wordSourceRepository.findAll()
				)
				.setUsers(
					userRepository.findAll()
				),
			outputStream
		);
	}
}
