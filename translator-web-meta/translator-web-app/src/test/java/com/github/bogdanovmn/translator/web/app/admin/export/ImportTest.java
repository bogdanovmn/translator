package com.github.bogdanovmn.translator.web.app.admin.export;

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
	public void userWordsLists() throws Exception {
		assertEquals("RememberedWords count" , 15, data.getUsers().get(0).getRememberedWords().size());
	}
}