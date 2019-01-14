package com.github.bogdanovmn.translator.core.text;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class NormalizedWords {
	private static final Logger LOG = LoggerFactory.getLogger(NormalizedWords.class);

	private final Set<String> words;
	private final Map<String, Set<String>> map = new HashMap<>();
	private boolean isPrepared = false;

	public NormalizedWords(Set<String> words) {
		this.words = words;
	}

	public Set<String> get() {
		this.prepare();
		return this.map.keySet();
	}

	private synchronized void prepare() {
		if (!this.isPrepared) {
			this.isPrepared = true;

			final List<EnglishWord> normalWords = new ArrayList<>();
			final Set<EnglishWord> formWords = new HashSet<>();

			words.stream()
				.sorted(Comparator.comparing(String::length))
				.map(EnglishWord::new)
				.forEach(word -> {
					if (word.withAnyPrefix() || word.withAnyPostfix()) {
						LOG.debug("word '{}' is form", word);
						formWords.add(word);
					}
					else {
						LOG.debug("word '{}' is normal", word);
						normalWords.add(word);
					}
				}
			);

			for (EnglishWord word : normalWords) {
				this.map.put(word.toString(), new HashSet<>());

				for (EnglishWord wordForm : word.possibleForms()) {
					if (formWords.contains(wordForm)) {
						LOG.debug("possible form '{}' match", wordForm);

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
						LOG.debug("alternative form base '{}' match with normal word", alternativeWordBase);
						wordBase = alternativeWordBase;
					}
				}

				forms.computeIfAbsent(
					wordBase.toString(),
					x -> new HashSet<>()
				).add(wordForm.toString());
			}

			forms.forEach(
				(baseWord, childs) -> {
					if (this.map.containsKey(baseWord)) {
						map.get(baseWord).addAll(childs);
					}
					else {
						String shortest = childs.stream()
							.min(
								Comparator.comparing(String::length)
							)
							.get();
						childs.remove(shortest);
						map.put(shortest, childs);
					}
			});
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
