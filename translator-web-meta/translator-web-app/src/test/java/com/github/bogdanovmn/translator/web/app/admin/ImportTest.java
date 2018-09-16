package com.github.bogdanovmn.translator.web.app.admin;

import com.github.bogdanovmn.translator.web.app.admin.export.ImportSchema;
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
		assertEquals("Source rawName", "ReferenceCardForMac.pdf", data.getSources().get(0).toDomain().getRawName());
	}

	@Test
	public void translateProviders() throws Exception {
		assertEquals("TranslateProviders count" , 1, data.getTranslateProviders().size());
		assertEquals("TranslateProvider name", "Google", data.getTranslateProviders().get(0).getName());
	}

	@Test
	public void translates() throws Exception {
		assertEquals("Translates count" , 2, data.getTranslates().size());
		assertEquals("Translate #1 provider", 1, data.getTranslates().get(0).getProviderId());

		assertEquals("Translate #2 provider", 1, data.getTranslates().get(1).getProviderId());
	}

	@Test
	public void userWordsLists() throws Exception {
		assertEquals("HoldOverWords count" , 2, data.getUsers().get(0).getHoldOverWords().size());
		assertEquals("RememberedWords count" , 2, data.getUsers().get(0).getRememberedWords().size());
	}
}