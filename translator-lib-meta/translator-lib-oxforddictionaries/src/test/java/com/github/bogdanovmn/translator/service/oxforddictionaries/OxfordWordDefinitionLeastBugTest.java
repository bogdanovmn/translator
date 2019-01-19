package com.github.bogdanovmn.translator.service.oxforddictionaries;

import com.github.bogdanovmn.translator.core.definition.DefinitionInstance;
import com.github.bogdanovmn.translator.core.definition.PartOfSpeech;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class OxfordWordDefinitionLeastBugTest {

	private static List<DefinitionInstance> definitions;

	@BeforeClass
	public static void init() throws Exception {
		String html = new String(
			Files.readAllBytes(
				Paths.get(
					OxfordWordDefinitionLeastBugTest.class.getResource("/definition--least--bug--html").toURI()
				)
			),
			StandardCharsets.UTF_8
		);
		definitions = new OxfordWordDefinition().parsedServiceResponse(html);
	}

	@Test
	public void shouldBeOnlyOneInstance() throws Exception {
		assertEquals(
			1,
			definitions.size()
		);
	}
	@Test
	public void shouldParsePronunciation() throws Exception {
		assertEquals(
			"/liːst/",
			definitions.get(0).pronunciation()
		);
	}
	@Test
	public void shouldParsePartOfSpeechNames() throws Exception {
		assertEquals(
			Arrays.asList("determiner & pronoun", "adverb", "adjective"),
			definitions.get(0).partOfSpeeches().stream()
				.map(PartOfSpeech::name).collect(Collectors.toList())
		);
	}
}