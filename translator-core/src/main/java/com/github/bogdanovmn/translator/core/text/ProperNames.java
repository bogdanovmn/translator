package com.github.bogdanovmn.translator.core.text;

import com.github.bogdanovmn.translator.core.StringCounter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


class ProperNames {
	private final Set<String> names;

	private ProperNames(Set<String> names) {
		this.names = names;
	}

	static ProperNames fromWordTokens(Tokens tokens) {
		Set<String> regularTokens = new HashSet<>();
		StringCounter tokenWithFirstCapital = new StringCounter();
		StringCounter tokenWithFirstCapitalInMiddleOfSentence = new StringCounter();
		List<Token> allTokens = tokens.all();
		for (int i = 0; i < allTokens.size(); i++) {
			Token currentToken = allTokens.get(i);
			if (currentToken.isFirstCapital()) {
				String word = currentToken.toString().toLowerCase();
				tokenWithFirstCapital.increment(word);
				if (i != 0 && allTokens.get(i - 1).isWord()) {
					tokenWithFirstCapitalInMiddleOfSentence.increment(word);
				}
			}
			else if (currentToken.isWord()){
				regularTokens.add(currentToken.toString());
			}
		}
		// Remove false positives
		Set<String> firstCapitalWordToIgnore = new HashSet<>();
		tokenWithFirstCapital.keys().forEach(
			token -> {
				if (regularTokens.contains(token)
						|| tokenWithFirstCapitalInMiddleOfSentence.get(token) < 1
						|| tokenWithFirstCapital.get(token) < 2
				) {
					firstCapitalWordToIgnore.add(token);
				}
			}
		);

		return new ProperNames(
			tokenWithFirstCapital.keys().stream()
				.filter(word -> !firstCapitalWordToIgnore.contains(word))
				.collect(Collectors.toSet()));
	}

	public boolean contains(String token) {
		return names.contains(token.toLowerCase());
	}
}
