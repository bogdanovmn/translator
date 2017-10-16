package com.github.bogdanovmn.translator.core;

import java.util.*;

public class NormalizedWords {
	private final Collection<String> words;
	private final static List<String> POSTFIXES = Arrays.asList("s", "ing", "ed");
	private final static List<String> PREFIXES = Arrays.asList("re", "sub");
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
			Set<String> forms = new HashSet<>();

			for (String word : this.words) {
				for (String postfix : POSTFIXES) {
					if (!word.endsWith(postfix)) {
						String wordForm = word + postfix;
						if (words.contains(wordForm)) {
							this.map.computeIfAbsent(word, x -> new HashSet<>())
								.add(wordForm);

							forms.add(wordForm);
						}
					}
				}

				for (String prefix : PREFIXES) {
					if (!word.startsWith(prefix)) {
						String wordForm = prefix + word;
						if (words.contains(wordForm)) {
							this.map.computeIfAbsent(word, x -> new HashSet<>())
								.add(wordForm);

							forms.add(wordForm);
						}
					}
				}

				if (!forms.contains(word)) {
					this.map.putIfAbsent(word, null);
				}

			}
			this.isPrepared = true;
		}
	}

	public Set<String> getFormsForWord(String word) {
		this.prepare();
		Set<String> forms = this.map.get(word);
		return forms != null
			? Collections.unmodifiableSet(forms)
			: null;
	}

	public void printWordsWithForms() {
		this.prepare();
		this.map.keySet().stream()
			.filter(x -> map.get(x) != null)
			.sorted(
				Comparator.comparing(String::length)
			)
			.forEach(x ->
				System.out.println(
					String.format("%s --> %s", x, map.get(x))
				)
			);
	}
}
