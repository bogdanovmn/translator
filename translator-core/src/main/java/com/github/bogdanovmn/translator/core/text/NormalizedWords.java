package com.github.bogdanovmn.translator.core.text;

import com.github.bogdanovmn.translator.core.BigString;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class NormalizedWords {
	private Map<String, Set<String>> map;

	private NormalizedWords(Map<String, Set<String>> formsMap) {
		this.map = formsMap;
	}

	public static NormalizedWords of(Set<String> words) {
		Map<String, Set<String>> map = new HashMap<>();

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
			map.put(word.toString(), new HashSet<>());

			for (EnglishWord wordForm : word.possibleForms()) {
				if (formWords.contains(wordForm)) {
					LOG.debug("possible form '{}' match", wordForm);

					map.get(word.toString())
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
				if (map.containsKey(baseWord)) {
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

		return new NormalizedWords(map);
	}

	public Set<String> get() {
		return Collections.unmodifiableSet(
			map.keySet()
		);
	}

	public Optional<Set<String>> wordForms(String word) {
		Set<String> forms = map.get(word);
		return forms != null
			? Optional.of(Collections.unmodifiableSet(forms))
			: Optional.empty();
	}

	public String wordsWithFormsStatistic() {
		BigString result = new BigString();
		this.map.keySet().stream()
			.filter(x -> !map.get(x).isEmpty())
			.sorted(
//				Comparator.comparing(String::length)
			)
			.forEach(x ->
				result.addLine(
					map.get(x).isEmpty()
						? x
						: String.format("%s --> %s", x, map.get(x))
				)
			);
		return result.toString();
	}
}
