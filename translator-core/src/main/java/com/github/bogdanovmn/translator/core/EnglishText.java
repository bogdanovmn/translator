package com.github.bogdanovmn.translator.core;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EnglishText {
	private final static String CONSONANT_LETTERS = "qwrtpsdfghkljzxcvbnm";
	private final String text;
	private final Map<String, Integer> words = new HashMap<>();
	private final Map<String, Integer> ignoreTokens = new HashMap<>();
	private boolean isAlreadyParsed = false;
	private NormalizedWords normalizedWords;

	public EnglishText(String text) {
		this.text = text;
	}

	private void parse() {
		if (!this.isAlreadyParsed) {
			String[] tokens = this.joinWraps(this.text.split("[^a-zA-Z-]+"));
			for (String token : tokens) {
				for (String normalizedToken : token.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase().split("_|-")) {
					if (
						(normalizedToken.length() < EnglishWord.MIN_BASE_LENGTH)
							||
							(normalizedToken.length() < 5 && normalizedToken.matches(".*[" + CONSONANT_LETTERS + "]{3}.*"))
							||
							(normalizedToken.matches(".*[" + CONSONANT_LETTERS + "]{5}.*"))
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
			this.normalizedWords = new NormalizedWords(this.words.keySet());
			this.isAlreadyParsed = true;
		}
	}

	private String[] joinWraps(String[] tokens) {
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].endsWith("-") && i < (tokens.length - 1)) {
				tokens[i] = tokens[i].replaceFirst("-*$", "") + tokens[i + 1];
				tokens[i + 1] = "";
			}
		}
		return tokens;
	}

	public Set<String> words() {
		this.parse();
		return this.words.keySet();
	}

	public Set<String> normalizedWords() {
		this.parse();
		return this.normalizedWords.get();
	}

	public int getWordFrequance(String word) {
		this.parse();
		return this.words.getOrDefault(word, 0);
	}

	public int getWordFormsFrequance(String word) {
		this.parse();
		int result = 0;
		if (this.words.containsKey(word)) {
			result += this.words.get(word);
			Set<String> forms = this.normalizedWords.getFormsForWord(word);
			if (forms != null) {
				for (String wordForm : forms) {
					result += this.words.get(wordForm);
				}
			}
		}
		return result;
	}

	public void printStatistic() {
		this.parse();

		System.out.println("---- Statistic ----");
		this.printTokens(this.words);

		System.out.println("---- Word forms ----");
		this.normalizedWords.printWordsWithForms();

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
