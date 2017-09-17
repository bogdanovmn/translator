package com.github.bogdanovmn.translator.core;

import java.util.*;

public class EnglishText {
	private final static String VOWELS = "qwrtpsdfghkljzxcvbnm";
	private final String text;
	private final Map<String, Integer> words = new HashMap<>();
	private final Map<String, Integer> ignoreTokens = new HashMap<>();
	private boolean isAlreadyParsed = false;

	public EnglishText(String text) {
		this.text = text;
	}

	private void parse() {
		if (!this.isAlreadyParsed) {
			for (String token : this.text.split("(\\W|\\d)+")) {
				for (String normalizedToken : token.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase().split("_")) {
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
		this.printTokens(this.words);

		System.out.println("---- Ignore statistic ----");
		this.printTokens(this.ignoreTokens);

		System.out.println(
			String.format(
				"Total %d passed (%d unique)\nIgnored %d (%d unique)",
					this.words.values().stream().mapToInt(Integer::intValue).sum(),
					this.words.size(),
					this.ignoreTokens.values().stream().mapToInt(Integer::intValue).sum(),
					this.ignoreTokens.size()
				)
		);
	}

	private void printTokens(Map<String, Integer> tokensCache) {
		tokensCache.keySet().stream()
			.sorted(
				Comparator.comparing((String x) -> tokensCache.get(x))
					.thenComparing(String::length)
			)
			.forEach(x ->
				System.out.println(
					String.format(
						"[%3d] %s", tokensCache.get(x), x
					)
				)
			);
	}
}
