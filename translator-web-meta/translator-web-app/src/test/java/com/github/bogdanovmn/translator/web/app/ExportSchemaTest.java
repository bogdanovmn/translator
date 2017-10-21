package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.web.orm.SourceRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import java.io.FileOutputStream;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {App.class})
public class ExportSchemaTest {
	@Autowired
	private SourceRepository sourceRepository;

	@Test
	@Ignore
	public void createXml() throws Exception {
		Marshaller marshaller = JAXBContext.newInstance(ExportSchema.class)
			.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

		marshaller.marshal(
			new ExportSchema()
				.setSources(
					this.sourceRepository.findAll()
				),
			new FileOutputStream("export.xml")
		);
	}

}