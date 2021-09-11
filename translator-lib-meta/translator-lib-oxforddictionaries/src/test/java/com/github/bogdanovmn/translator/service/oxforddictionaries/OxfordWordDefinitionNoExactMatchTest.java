package com.github.bogdanovmn.translator.service.oxforddictionaries;

import com.github.bogdanovmn.httpclient.core.ResponseNotFoundException;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class OxfordWordDefinitionNoExactMatchTest {

	@Test(expected = ResponseNotFoundException.class)
	public void shouldBeOnlyOneInstance() throws Exception {
		String html = new String(
			Files.readAllBytes(
				Paths.get(
					OxfordWordDefinitionNoExactMatchTest.class.getResource("/definition--xxx--no-exact-match--html").toURI()
				)
			),
			StandardCharsets.UTF_8
		);
		try {
			new OxfordWordDefinition().parsedServiceResponse(html);
		}
		catch (ResponseNotFoundException e) {
			assertEquals(
				"Definition not found",
				e.getMessage()
			);
			throw e;
		}
	}
}