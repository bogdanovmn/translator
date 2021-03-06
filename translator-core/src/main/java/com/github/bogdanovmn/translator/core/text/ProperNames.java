package com.github.bogdanovmn.translator.core.text;

import com.github.bogdanovmn.common.core.StringCounter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ProperNames {
	private final StringCounter names;

	private ProperNames(StringCounter names) {
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

		firstCapitalWordToIgnore.forEach(tokenWithFirstCapital::remove);
		return new ProperNames(tokenWithFirstCapital);
	}

	public boolean contains(String token) {
		return names.get(token.toLowerCase()) > 0;
	}

	public long frequency(String name) {
		return names.get(name.toLowerCase());
	}

	public Set<String> names() {
		return names.keys();
	}
}
