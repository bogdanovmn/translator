package com.github.bogdanovmn.translator.core.text;

import com.github.bogdanovmn.common.core.BigString;
import com.github.bogdanovmn.common.core.StringCounter;
import com.github.bogdanovmn.common.log.Timer;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;


public class EnglishText implements TextContent {
	private final static String CONSONANT_LETTERS = "qwrtpsdfghkljzxcvbnm";

	private final StringCounter wordsCounter;
	private final StringCounter ignoreWordsCounter;
	private NormalizedWords normalizedWords;

	private EnglishText(StringCounter wordsCounter, StringCounter ignoreWordsCounter) {
		this.wordsCounter = wordsCounter;
		this.ignoreWordsCounter = ignoreWordsCounter;
		this.normalizedWords = NormalizedWords.of(wordsCounter.keys());

	}

	public static EnglishText fromText(String text) {
		StringCounter wordsCounter = new StringCounter();
		StringCounter ignoreWordsCounter = new StringCounter();

		Tokens tokens = JoinWrapsTokens.of(
			Tokens.of(text)
		);

		ProperNames properNames = Timer.measure("Proper names", () -> ProperNames.fromWordTokens(tokens));

		for (String token : tokens.wordsWithoutCapslock()) {
			for (String normalizedToken : token.replaceAll("([A-Z])", "_$1").toLowerCase().split("[_-]")) {
				if (
					(normalizedToken.length() < EnglishWord.MIN_BASE_LENGTH)
						||
						(normalizedToken.matches("^[" + CONSONANT_LETTERS + "]+$"))
						||
						(normalizedToken.length() < 5 && normalizedToken.matches(".*[" + CONSONANT_LETTERS + "]{3,}.*"))
						||
						(normalizedToken.matches(".*[" + CONSONANT_LETTERS + "]{5,}.*"))
						||
						normalizedToken.matches(".*(.)\\1{2,}.*")
						||
						properNames.contains(normalizedToken)
					) {
					if (normalizedToken.length() > 1) {
						ignoreWordsCounter.increment(normalizedToken);
					}
					continue;
				}
				wordsCounter.increment(normalizedToken);
			}
		}
		return new EnglishText(wordsCounter, ignoreWordsCounter);
	}

	@Override
	public Set<String> uniqueWords() {
		return wordsCounter.keys();
	}

	@Override
	public Set<String> normalizedWords() {
		return normalizedWords.get();
	}

	@Override
	public int wordFrequency(String word) {
		int result = wordsCounter.get(word);
		if (result > 0) {
			Optional<Set<String>> forms = normalizedWords.wordForms(word);
			if (forms.isPresent()) {
				for (String wordForm : forms.get()) {
					result += wordsCounter.get(wordForm);
				}
			}
		}
		return result;
	}

	public String statistic() {
		Set<String> words = wordsCounter.keys();
		Set<String> ignoreWords = ignoreWordsCounter.keys();

		BigString result = new BigString()
			.addLine("---- Statistic ----")
			.addLine(
				tokensStatistic(wordsCounter, "W")
			)
			.addLine("---- Word forms ----")
			.addLine(
				normalizedWords.wordsWithFormsStatistic()
			)
			.addLine("---- Ignore statistic ----")
			.addLine(
				tokensStatistic(ignoreWordsCounter, "I")
			)
			.addLine("Total %d passed (%d unique)\nIgnored %d (%d unique)",
				words.stream().map(wordsCounter::get).mapToInt(Integer::intValue).sum(),
				words.size(),
				ignoreWords.stream().map(ignoreWordsCounter::get).mapToInt(Integer::intValue).sum(),
				ignoreWords.size()
			);
		return result.toString();
	}

	private String tokensStatistic(StringCounter tokensCache, String prefix) {
		BigString result = new BigString();
		tokensCache.keys().stream()
			.sorted(
				Comparator.comparing(tokensCache::get)
					.thenComparing(String::length)
			)
			.forEach(x ->
				result.add("%s [%3d] %s%n", prefix, tokensCache.get(x), x)
			);
		return result.toString();
	}
}
