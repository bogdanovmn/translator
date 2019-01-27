package com.github.bogdanovmn.translator.service.oxforddictionaries;

import com.github.bogdanovmn.httpclient.simple.SimpleHttpClient;
import com.github.bogdanovmn.translator.core.ResponseException;
import com.github.bogdanovmn.translator.core.ResponseNotFoundException;
import com.github.bogdanovmn.translator.core.ResponseParseException;
import com.github.bogdanovmn.translator.core.definition.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class OxfordWordDefinition extends HttpWordDefinitionService {

	public static final String URL_PREFIX = "https://en.oxforddictionaries.com/definition/";

	public OxfordWordDefinition() {
		super(
			new SimpleHttpClient(),
			URL_PREFIX
		);
	}

	@Override
	protected List<DefinitionInstance> parsedServiceResponse(String htmlText, String word)
		throws ResponseException
	{
		List<DefinitionInstance> result = new ArrayList<>();
		Document doc = Jsoup.parse(htmlText);

		String articleWord = articleWord(doc);
		boolean anotherWordForm = false;
		if (!articleWord.equals(word)) {
			if (Character.isUpperCase(articleWord.charAt(0))) {
				throw new ResponseNotFoundException(
					String.format("Proper name: '%s'", articleWord)
				);
			}
			anotherWordForm = true;
		}

		DefinitionInstance.DefinitionInstanceBuilder currentDefInstBuilder = null;
		Elements blocks = doc.select("div[class*=entryHead],section[class=gramb],section[class*=pronSection]");
		for (Element block : blocks) {
			if (block.nodeName().equals("div")) {
				if (currentDefInstBuilder != null) {
					result.add(currentDefInstBuilder.build());
				}
				currentDefInstBuilder = DefinitionInstance.builder();
			}
			else {
				if (currentDefInstBuilder == null) {
					throw new ResponseParseException("Wrong blocks order");
				}
				else if (block.hasClass("gramb")) {
					currentDefInstBuilder.partOfSpeech(
						parsedPartOfSpeech(block)
					);
				}
				else if (block.hasClass("pronSection")) {
					currentDefInstBuilder.pronunciation(
						block.select("span[class=phoneticspelling]").text()
					);
				}
			}
		}
		if (currentDefInstBuilder == null) {
			throw new ResponseParseException("No blocks found");
		}
		result.add(currentDefInstBuilder.build());

		if (anotherWordForm) {
			throw new ResponseAnotherWordFormException(
				articleWord, result
			);
		}

		return result;
	}

	private String articleWord(Document doc) throws ResponseException {
		Element noExactMatchBlock = doc.select("div[class=no-exact-matches] h2[class=searchHeading]").first();
		if (noExactMatchBlock != null && noExactMatchBlock.text().startsWith("No exact matches found for")) {
			throw new ResponseNotFoundException("Definition not found");
		}
		Element headerBlock = doc.select("div[class*=entryHead] header h1 em").first();
		if (!headerBlock.hasText()) {
			throw new ResponseParseException("Article word not found");
		}
		return headerBlock.text();
	}

	private PartOfSpeech parsedPartOfSpeech(Element block) {
		PartOfSpeech.PartOfSpeechBuilder partOfSpeech = PartOfSpeech.builder()
			.name(
				block.select("span[class=pos]").first().text()
			);

		Element grammaticalNoteBlock = block.select("span[class=transitivity]").first();
		if (grammaticalNoteBlock.hasText()) {
			partOfSpeech.grammaticalNote(
				trim(
					grammaticalNoteBlock.text()
				)
			);
		}

		Elements sentencesBlocks = block.select("ul[class=semb] > li > div[class=trg]");
		for (Element sentenceBlock : sentencesBlocks) {
			partOfSpeech.sentence(
				parsedSentence(sentenceBlock, false)
			);
		}
		return partOfSpeech.build();
	}

	private Sentence parsedSentence(Element block, boolean subSentence) {
		Element descriptionBlock = block.select("span[class=ind]").first();
		Sentence.SentenceBuilder sentence = Sentence.builder()
			.description(
				descriptionBlock == null ? null : descriptionBlock.text()
			);

		Element descriptionGrammaticalNoteBlock = block.select("span[class=grammatical_note]").first();
		if (descriptionGrammaticalNoteBlock != null) {
			sentence.grammaticalNote(
				trim(
					descriptionGrammaticalNoteBlock.text()
				)
			);
		}

		Element crossReferenceBlock = block.select("div[class=crossReference]").first();
		if (crossReferenceBlock != null && crossReferenceBlock.hasText()) {
			sentence
				.description(crossReferenceBlock.text())
				.crossReference(true);
		}
		else {
			Elements exampleBlocks = block.select("div[class=exg] div[class=ex]");
			Elements moreExampleBlocks = block.select("div[class=exg] li[class=ex] em");
			Elements synonymBlocks = block.select("div[class=synonyms] div[class=exs]");
			Elements subSentenceBlocks = block.select("li[class=subSense]");

			// Primary examples

			for (Element exampleBlock : exampleBlocks) {
				Example.ExampleBuilder example = Example.builder()
					.primary(true);

				Element grammaticalNoteBlock = exampleBlock.select("span[class=grammatical_note]").first();
				if (grammaticalNoteBlock != null) {
					example.grammaticalNote(
						grammaticalNoteBlock.text()
					);
				}
				example.value(
					trim(
						exampleBlock.select("em").text()
					)
				);
				sentence.example(example.build());
			}

			// More examples

			for (Element exampleBlock : moreExampleBlocks) {
				sentence.example(
					Example.builder()
						.primary(false)
						.value(
							trim(
								exampleBlock.select("em").text()
							)
						)
						.build()
				);
			}

			// Synonyms

			for (Element synonymBlock : synonymBlocks) {
				List<String> moreSynonyms = new LinkedList<>(
					Arrays.asList(
						synonymBlock.text().split("\\s*,\\s*")
					)
				);
				moreSynonyms.remove(0);

				sentence.synonym(
					Synonym.builder()
						.value(
							synonymBlock.select("strong").text()
						)
						.more(moreSynonyms)
						.build()
				);
			}

			// Sub senses

			if (!subSentence) {
				for (Element senseBlock : subSentenceBlocks) {
					sentence.subSentence(
						parsedSentence(senseBlock, true)
					);
				}
			}
		}
		return sentence.build();
	}

	private String trim(String value) {
		return value != null && value.length() > 2
			? value.replaceAll("^\\W(.*)\\W$", "$1")
			: value;
	}
}
