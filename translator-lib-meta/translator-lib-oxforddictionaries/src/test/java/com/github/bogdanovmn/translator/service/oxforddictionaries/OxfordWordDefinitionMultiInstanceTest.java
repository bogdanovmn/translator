package com.github.bogdanovmn.translator.service.oxforddictionaries;

import com.github.bogdanovmn.translator.core.definition.DefinitionInstance;
import com.github.bogdanovmn.translator.core.definition.PartOfSpeech;
import com.github.bogdanovmn.translator.core.definition.Sentence;
import com.github.bogdanovmn.translator.core.definition.Synonym;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OxfordWordDefinitionMultiInstanceTest {
	private static List<DefinitionInstance> definitions;

	@BeforeClass
	public static void init() throws Exception {
		String html = new String(
			Files.readAllBytes(
				Paths.get(
					OxfordWordDefinitionMultiInstanceTest.class.getResource("/definition--bound--multi-instance--html").toURI()
				)
			),
			StandardCharsets.UTF_8
		);
		definitions = new OxfordWordDefinition().parsedServiceResponse(html, "bound");
	}

	@Test
	public void shouldParseAllInstances() throws Exception {
		assertEquals(
			4,
			definitions.size()
		);
	}

	@Test
	public void shouldParsePronunciation() throws Exception {
		assertEquals(
			"/baʊnd/",
			definitions.get(0).pronunciation()
		);
	}
	@Test
	public void shouldParsePartOfSpeechNames() throws Exception {
		assertEquals(
			Arrays.asList("verb", "adjective"),
			definitions.get(2).partOfSpeeches().stream()
				.map(PartOfSpeech::name).collect(Collectors.toList())
		);
	}

	@Test
	public void shouldParseCrossReference() throws Exception {
		PartOfSpeech verb = definitions.get(2).partOfSpeeches().get(0);

		assertEquals(
			"Cross reference has 1 sentence",
			1,
			verb.sentences().size()
		);

		Sentence verbSentence = verb.sentences().get(0);
		assertTrue(
			"Cross reference is cross reference",
			verbSentence.crossReference()
		);

		assertEquals(
			"Cross reference description",
			"past and past participle of bind",
			verbSentence.description()
		);

		assertEquals(
			"Cross reference without data",
			0,
			verbSentence.examples().size() + verbSentence.synonyms().size()
		);
	}

	@Test
	public void shouldParseSentenceDescriptionGrammaticalNote() throws Exception {
		Sentence sentence = definitions.get(2).partOfSpeeches().get(1).sentences().get(0);
		assertEquals(
			"with infinitive",
			sentence.grammaticalNote()
		);
	}

	@Test
	public void shouldParseSentences() throws Exception {
		assertEquals(
			"Adjective main sentences count",
			4,
			definitions.get(2).partOfSpeeches().get(1).sentences().size()
		);
	}

	@Test
	public void shouldParseSubSentences() throws Exception {
		Sentence withSubSentences = definitions.get(2).partOfSpeeches().get(1).sentences().get(3);
		assertEquals(
			"Adjective sub-sentences count",
				1,
				withSubSentences.subSentences().size()
		);

		assertEquals(
			"Adjective 4.1 sentence description",
				"In Chomskyan linguistics, (of a reflexive, reciprocal, or other linguistic unit) dependent for its reference on another noun phrase in the same sentence.",
				withSubSentences.subSentences().get(0).description()
		);

		assertEquals(
			"Adjective 4.1 sentence last example",
				"All nouns are bound by referents, and it is healthier to one's linguistic development to keep things less solid and grounded.",
				withSubSentences.subSentences().get(0).examples().get(6).value()
		);
	}

	@Test
	public void shouldParseSubSentenceSynonyms() throws Exception {
		Sentence subSentencesWithSynonyms = definitions.get(2).partOfSpeeches().get(1).sentences().get(0)
			.subSentences().get(0);

		assertEquals(
			"Sub sentence synonyms values",
			Collections.singletonList(
				"obligated"
			),
			subSentencesWithSynonyms.synonyms().stream()
				.map(Synonym::value)
				.collect(Collectors.toList())
		);

		assertEquals(
			"Sub sentence synonym 'obligated' more values",
			"obliged, under obligation, compelled, required, duty-bound, honour-bound, constrained",
			String.join(
				", ",
				subSentencesWithSynonyms.synonyms().get(0).more()
			)
		);
	}
}