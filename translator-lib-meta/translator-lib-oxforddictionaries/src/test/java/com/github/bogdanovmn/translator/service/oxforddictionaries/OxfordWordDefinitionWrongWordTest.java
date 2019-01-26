package com.github.bogdanovmn.translator.service.oxforddictionaries;

import com.github.bogdanovmn.translator.core.ResponseNotFoundException;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OxfordWordDefinitionWrongWordTest {

	@Test(expected = ResponseAnotherWordFormException.class)
	public void shouldFoundAnotherWordForm() throws Exception {
		String html = new String(
			Files.readAllBytes(
				Paths.get(
					OxfordWordDefinitionWrongWordTest.class.getResource("/definition--mans--wrong-word--html").toURI()
				)
			),
			StandardCharsets.UTF_8
		);
		try {
			new OxfordWordDefinition().parsedServiceResponse(html, "mans");
		}
		catch (ResponseAnotherWordFormException e) {
			assertEquals("man", e.getWord());
			assertNotNull(e.getDefinitions());
			throw e;
		}
	}
}