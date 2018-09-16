package com.github.bogdanovmn.translator.web.app.cloud;

import com.github.bogdanovmn.translator.orm.core.BaseEntityWithUniqueName;
import com.github.bogdanovmn.translator.web.orm.Word;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class CloudWords {
	private static final int MIN_SIZE_PERCENT = 45;
	private static final int MAX_SIZE_PERCENT = 615;

	private final static Pattern STOP_WORDS = Pattern.compile(
		"^(this|that|with|within|without|from|for|into|)$",
			Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE
	);


	private final List<Word> words;

	CloudWords(List<Word> words) {
		this.words = words;
	}

	List<CloudWord> words() {
		final int wordMaxFreq = words.stream().mapToInt(Word::getFrequence).max().orElse(0);

		return words.stream()
			.filter(word -> word.getFrequence() * 1000 / wordMaxFreq > 5) // > 0.5%
			.filter(word -> !STOP_WORDS.matcher(word.getName()).matches())
			.sorted(Comparator.comparing(BaseEntityWithUniqueName::getName))
			.map(
				word -> new CloudWord(
					word,
					word.getFrequence() * (MAX_SIZE_PERCENT - MIN_SIZE_PERCENT) / wordMaxFreq + MIN_SIZE_PERCENT
				)
			)
			.collect(Collectors.toList());
	}
}
