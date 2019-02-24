package com.github.bogdanovmn.translator.core.text;

import com.github.bogdanovmn.translator.core.StringCounter;

import java.util.HashSet;
import java.util.Set;

class ProperNames {
	private final StringCounter withFirstCapital;

	private ProperNames(StringCounter withFirstCapital) {
		this.withFirstCapital = withFirstCapital;
	}

	static ProperNames fromWordTokens(String[] tokens) {
		Set<String> regularTokens = new HashSet<>();
		StringCounter tokensWithFirstCapital = new StringCounter();
		for (String token : tokens) {
			if (token.matches("^[A-Z][a-z]+$")) {
				tokensWithFirstCapital.increment(token.toLowerCase());
			} else {
				regularTokens.add(token);
			}
		}
		regularTokens.forEach(
			token -> {
				if (tokensWithFirstCapital.get(token) > 0) {
					tokensWithFirstCapital.remove(token);
				}
			}
		);

		return new ProperNames(tokensWithFirstCapital);
	}

	public int weight(String token) {
		return withFirstCapital.get(token);
	}
}
