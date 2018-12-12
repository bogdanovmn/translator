package com.github.bogdanovmn.translator.service.oxforddictionaries;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public class OxfordWordDefinitionTest {

	@Test
	public void shouldParseSomeWordDefinition() throws Exception {
		String html = new String(
			Files.readAllBytes(
				Paths.get(
					getClass().getResource("/definition--some--html").toURI()
				)
			),
			"UTF8"
		);

		Set<String> definitions = new OxfordWordDefinition().parsedServiceRawAnswer(html);

	}
}