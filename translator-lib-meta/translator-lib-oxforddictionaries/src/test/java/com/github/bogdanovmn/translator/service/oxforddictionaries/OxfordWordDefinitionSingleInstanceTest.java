package com.github.bogdanovmn.translator.service.oxforddictionaries;

import com.github.bogdanovmn.translator.core.definition.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class OxfordWordDefinitionSingleInstanceTest {

	private static List<DefinitionInstance> definitions;

	@BeforeClass
	public static void init() throws Exception {
		String html = new String(
			Files.readAllBytes(
				Paths.get(
					OxfordWordDefinitionSingleInstanceTest.class.getResource("/definition--benefit--one-instance--html").toURI()
				)
			),
			StandardCharsets.UTF_8
		);
		definitions = new OxfordWordDefinition().parsedServiceResponse(html).instances();
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
			"/ˈbɛnɪfɪt/",
			definitions.get(0).pronunciation()
		);
	}
	@Test
	public void shouldParsePartOfSpeechNames() throws Exception {
		assertEquals(
			Arrays.asList("noun", "verb"),
			definitions.get(0).partOfSpeeches().stream()
				.map(PartOfSpeech::name).collect(Collectors.toList())
		);
	}

	@Test
	public void shouldParsePartOfSpeechGrammaticalNote() throws Exception {
		assertEquals(
			"no object",
			definitions.get(0).partOfSpeeches().get(1).grammaticalNote()
		);
	}
	@Test
	public void shouldNotParsePartOfSpeechGrammaticalNote() throws Exception {
		assertNull(
			definitions.get(0).partOfSpeeches().get(0).grammaticalNote()
		);
	}
	@Test
	public void shouldParseSentencesDescription() throws Exception {
		assertEquals(
			Arrays.asList(
				"An advantage or profit gained from something.",
				"A payment made by the state or an insurance scheme to someone entitled to receive it.",
				"An event such as a concert or game that is intended to raise money for a particular player or charity."
			),
			definitions.get(0).partOfSpeeches().get(0).sentences().stream()
				.map(Sentence::description)
				.collect(Collectors.toList())
		);
	}
	@Test
	public void shouldParseSentenceExampleGrammaticalNote() throws Exception {
		assertEquals(
			"mass noun",
			definitions.get(0).partOfSpeeches().get(0).sentences().get(0).examples().get(1).grammaticalNote()
		);
	}
	@Test
	public void shouldParseSentencePrimaryExamples() throws Exception {
		assertEquals(
			Arrays.asList(
				"enjoy the benefits of being a member",
				"the changes are of benefit to commerce"
			),
			definitions.get(0).partOfSpeeches().get(0).sentences().get(0).examples().stream()
				.filter(Example::primary)
				.map(Example::value)
				.collect(Collectors.toList())
		);
	}
	@Test
	public void shouldParseSentenceMoreExamples() throws Exception {
		PartOfSpeech noun = definitions.get(0).partOfSpeeches().get(0);

		long count = noun.sentences().get(0).examples().stream()
			.filter(x -> !x.primary())
			.peek(example ->
				assertTrue(
					!example.value().isEmpty()
				)
			)
			.count();

		assertEquals(
			"Examples count", 20, count
		);

		assertEquals(
			"More examples first value",
			"With the benefit of that information in front of her, she confirmed that there was nothing to worry about in the story the previous week.",
			noun.sentences().get(0).examples().stream()
				.filter(x -> !x.primary())
				.findFirst()
				.map(Example::value)
				.orElse("")

		);

	}

	@Test
	public void shouldParseSentenceSynonyms() throws Exception {

		PartOfSpeech noun = definitions.get(0).partOfSpeeches().get(0);
		assertEquals(
			"Noun first sentence synonyms values",
			Arrays.asList(
				"good", "advantage"
			),
			noun.sentences().get(0).synonyms().stream()
				.map(Synonym::value)
				.collect(Collectors.toList())
		);

		assertEquals(
			"Noun first sentence synonym 'good' more values",
			"sake, interest, welfare, well-being, satisfaction, enjoyment, advantage, comfort, ease, convenience",
			String.join(
				", ",
				noun.sentences().get(0).synonyms().get(0).more()
			)
		);
	}
}