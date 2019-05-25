package com.github.bogdanovmn.translator.web.app.admin.export;

import com.github.bogdanovmn.translator.web.orm.entity.UserRepository;
import com.github.bogdanovmn.translator.web.orm.entity.WordRepository;
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

	void export(OutputStream outputStream)
		throws JAXBException
	{
		Marshaller marshaller = JAXBContext.newInstance(ExportSchema.class)
			.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

		marshaller.marshal(
			new ExportSchema()
//				.setWords(
//					wordRepository.findAll()
//				)
				.setUsers(
					userRepository.findAll()
				),
			outputStream
		);
	}
}
