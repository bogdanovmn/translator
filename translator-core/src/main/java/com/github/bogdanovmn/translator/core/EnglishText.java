package com.github.bogdanovmn.translator.core;

import java.util.*;

public class EnglishText {
	private final String VOWELS = "qwrtpsdfghkljzxcvbnm";
	private final String text;
	private final Map<String, Integer> words = new HashMap<>();
	private final Map<String, Integer> ignoreTokens = new HashMap<>();
	private boolean isAlreadyParsed = false;

	public EnglishText(String text) {
		this.text = text;
	}

	private void parse() {
		if (!this.isAlreadyParsed) {
			for (String token : this.text.split("(\\W|\\d|_)+")) {
				String normalizedToken = token.toLowerCase();

				if (
					(normalizedToken.length() < 3)
					||
					(normalizedToken.length() < 5 && normalizedToken.matches(".*[" + VOWELS + "]{3}.*"))
					||
					(normalizedToken.matches(".*[" + VOWELS + "]{5}.*"))
					||
					normalizedToken.matches(".*(.)\\1{2,}.*")
				) {
					this.ignoreTokens.put(
						normalizedToken,
						this.ignoreTokens.getOrDefault(normalizedToken, 0) + 1
					);
					continue;
				}

				this.words.put(
					normalizedToken,
					this.words.getOrDefault(normalizedToken, 0) + 1
				);
			}
			this.isAlreadyParsed = true;
		}
	}

	public Set<String> words() {
		this.parse();
		return this.words.keySet();
	}

	public int getWordFrequance(String word) {
		this.parse();
		return this.words.getOrDefault(word, 0);
	}

	public void printStatistic() {
		this.parse();
		System.out.println("---- Statistic ----");
		this.words.keySet().stream()
			.sorted(Comparator.comparingLong(words::get))
			.forEach(x ->
				System.out.println(
					String.format(
						"[%3d] %s", words.get(x), x
					)
				)
			);

		System.out.println("---- Ignore statistic ----");
		this.ignoreTokens.keySet().stream()
			.sorted(Comparator.comparingLong(ignoreTokens::get))
			.forEach(x ->
				System.out.println(
					String.format(
						"[%3d] %s", ignoreTokens.get(x), x
					)
				)
			);
	}
}
