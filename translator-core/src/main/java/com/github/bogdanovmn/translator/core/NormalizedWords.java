package com.github.bogdanovmn.translator.core;

import java.util.*;

public class NormalizedWords {
	private final Collection<String> words;
	private final static List<String> POSTFIXES = Arrays.asList("s", "ing");
	private final Map<String, Set<String>> map = new HashMap<>();
	private boolean isPrepared = false;

	public NormalizedWords(Collection<String> words) {
		this.words = words;
	}

	public Set<String> get() {
		this.prepare();
		return this.map.keySet();
	}

	private void prepare() {
		if (!this.isPrepared) {
			for (String word : this.words) {
				for (String postfix : POSTFIXES) {
					if (!word.endsWith(postfix)) {
						String wordForm = word + postfix;
						if (words.contains(wordForm)) {
							this.map.computeIfAbsent(word, x -> new HashSet<>())
								.add(wordForm);
						}
					}
				}
			}
			this.isPrepared = true;
		}
	}

	public void printWordsWithForms() {
		this.prepare();
		this.map.keySet().stream()
			.sorted(
				Comparator.comparing(String::length)
			)
			.forEach(x ->
				System.out.println(
					String.format(
						"%s --> %s", x, map.get(x)
					)
				)
			);
	}
}
