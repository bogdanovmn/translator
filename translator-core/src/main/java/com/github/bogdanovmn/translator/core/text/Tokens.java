package com.github.bogdanovmn.translator.core.text;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Tokens {
	private final List<Token> tokens;

	Tokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	static Tokens of(String text) {
		List<Token> tokens = new ArrayList<>();

		for (String line : prepareText(text).split("\\r?\\n")) {
			if (line.matches("^\\s*$")) {
				tokens.add(Token.emptyLine());
			}
			else {
				tokens.addAll(
					Stream.of(
						line.replaceAll("([a-zA-Z-])([^a-zA-Z- ])", "$1 $2")
							.replaceAll("([^a-zA-Z- ])([a-zA-Z-])", "$1 $2")
							.replaceAll("([^a-zA-Z-])(\")", "$1 $2") // sentence end too
							.split("\\s+")
					).map(Token::new)
					.collect(Collectors.toList())
				);
			}
		}
		return new Tokens(tokens);
	}

	private static String prepareText(String text) {
		return text.replaceAll("\\p{Pd}+", "-")
			.replaceAll("['\"“”‘’„”«»]", Token.QUOTE)
			.replaceAll("[.!?]+", Token.DOT) // sentence end
			.replaceAll("&#\\d+;", " ")
			.replaceAll("\\b(\\w|\\d)+\\d\\S+", " ") // like "42D5GrxOQFebf83DYgNl-g"
			.replaceAll("\\b[A-Z][A-Z-]*\\d+[A-Z\\d]*\\b", " ") // like UTF-8 or KOI-8R
			.replaceAll("\\w+://\\S+", " ") // URLs
			.replaceAll("\\S+\\.\\w{2,3}\\s", " ") // like URLs without protocol or properties
			.replaceAll("\\s+", " ");

	}

	List<Token> all() {
		return Collections.unmodifiableList(tokens);
	}

	List<String> wordsWithoutCapslock() {
		return tokens.stream()
			.filter(Token::isWord)
			.map(t -> t.isCapital() ? t.toString().toLowerCase() : t.toString())
			.collect(Collectors.toList());
	}
}
