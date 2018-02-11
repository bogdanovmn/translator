package com.github.bogdanovmn.translator.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class NormalizedWords {
	private static final Logger LOG = LoggerFactory.getLogger(NormalizedWords.class);

	private final Collection<String> words;
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
			this.isPrepared = true;

			Set<EnglishWord> normalWords = new HashSet<>();
			Set<EnglishWord> formWords = new HashSet<>();

			for (String wordString : this.words) {
				EnglishWord word = new EnglishWord(wordString);
				if (word.withAnyPrefix() || word.withAnyPostfix()) {
					formWords.add(word);
				}
				else {
					normalWords.add(word);
				}
			}

			for (EnglishWord word : normalWords) {
				this.map.put(word.toString(), new HashSet<>());
				for (EnglishWord wordForm : word.posibleForms()) {
					if (formWords.contains(wordForm)) {
						this.map.get(word.toString())
							.add(wordForm.toString());
						formWords.remove(wordForm);
					}
				}
			}

			Map<String, Set<String>> forms = new HashMap<>();
			for (EnglishWord wordForm : formWords) {
				// Если словоформа осталась в этом списке, значит базового слова для нее не нашлось
				// но могут найтись другие словоформы
				// Мапить по базе их нельзя, т.к. она может быть "обрезанной"
				EnglishWord wordBase = wordForm.base();

				LOG.debug("form: '{}' --> base: '{}'", wordForm, wordBase);

				if (!wordBase.toString().endsWith("e")) {
					EnglishWord alternativeWordBase = new EnglishWord(wordBase.toString() + "e");
					if (normalWords.contains(alternativeWordBase)) {
						wordBase = alternativeWordBase;
					}
				}

				forms.computeIfAbsent(
					wordBase.toString(),
					x -> new HashSet<>()
				).add(wordForm.toString());
			}
			for (Set<String> f : forms.values()) {
				String shortest = f.stream()
					.sorted(
						Comparator.comparing(String::length)
					)
					.findFirst()
					.get();

				f.remove(shortest);
				this.map.put(shortest, f);
			}
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
//				Comparator.comparing(String::length)
			)
			.forEach(x ->
				System.out.println(
					map.get(x).isEmpty()
						? x
						: String.format("%s --> %s", x, map.get(x))
				)
			);
	}
}
