package com.github.bogdanovmn.translator.web.app;

import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ImportTest {
	private ImportSchema data;

	@Before
	public void setup() throws IOException, JAXBException {
		this.data = (ImportSchema) JAXBContext.newInstance(ImportSchema.class)
			.createUnmarshaller().unmarshal(
				getClass().getResourceAsStream("/import.xml")
			);
	}

	@Test
	public void importCheckUnmarshal() throws Exception {
		assertNotNull(data);
	}

	@Test
	public void sources() throws Exception {
		assertEquals("Sources count", 2, data.getSources().size());
		assertEquals("Source rawName", "ReferenceCardForMac.pdf", data.getSources().get(0).getRawName());
	}

	@Test
	public void translates() throws Exception {
		assertEquals("Translates count" , 2, data.getTranslates().size());
		assertEquals("Translate #1 provider", 1, data.getTranslates().get(0).getProviderId());

		assertEquals("Translate #2 provider", 1, data.getTranslates().get(1).getProviderId());
	}

}