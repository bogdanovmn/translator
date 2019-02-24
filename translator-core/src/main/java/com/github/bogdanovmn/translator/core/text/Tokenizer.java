package com.github.bogdanovmn.translator.core.text;


import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

class Tokenizer {
	private final List<String> tokens;

	private final static String EMPTY_LINE = "";
	private final static String QUOTE = "\"";
	private final static String DOT = ".";

	Tokenizer(List<String> tokens) {
		this.tokens = tokens;
	}

	static Tokenizer of(String text) {
		List<String> tokens = new LinkedList<>();

		for (String line : prepareText(text).split("\\r?\\n")) {
			if (line.matches("^\\s*$")) {
				tokens.add(EMPTY_LINE);
			}
			else {
				System.out.println(line);
				System.out.println(line.replaceAll("([a-zA-Z-])([^a-zA-Z- ])", "$1 $2"));
				System.out.println(
					line.replaceAll("([a-zA-Z-])([^a-zA-Z- ])", "$1 $2")
						.replaceAll("([^a-zA-Z- ])([a-zA-Z-])", "$1 $2")
				);
				tokens.addAll(
					Arrays.asList(
						line.replaceAll("([a-zA-Z-])([^a-zA-Z- ])", "$1 $2")
							.replaceAll("([^a-zA-Z- ])([a-zA-Z-])", "$1 $2")
							.replaceAll("([^a-zA-Z-])(\")", "$1 $2") // sentence end too
							.split("\\s+")
					)
				);
			}
		}
		return new Tokenizer(tokens);
	}

	private static String prepareText(String text) {
		return text.replaceAll("\\p{Pd}", "-")
			.replaceAll("['\"“”‘’„”«»]", QUOTE)
			.replaceAll("[.!?]+", DOT) // sentence end
			.replaceAll("&#\\d+;", " ")
			.replaceAll("\\b(\\w|\\d)+\\d\\S+", " ") // like "42D5GrxOQFebf83DYgNl-g"
			.replaceAll("\\b[A-Z][A-Z-]*\\d+[A-Z\\d]*\\b", " ") // like UTF-8 or KOI-8R
			.replaceAll("\\w+://\\S+", " ") // URLs
			.replaceAll("\\S+\\.\\w{2,3}\\s", " ") // like URLs without protocol or properties
			.replaceAll("\\s+", " ");

	}

	List<String> tokens() {
		return Collections.unmodifiableList(tokens);
	}

	List<String> words() {
		return tokens.stream()
			.filter(t -> !EMPTY_LINE.equals(t) && !DOT.equals(t) && !QUOTE.equals(t))
			.collect(Collectors.toList());
	}
}
