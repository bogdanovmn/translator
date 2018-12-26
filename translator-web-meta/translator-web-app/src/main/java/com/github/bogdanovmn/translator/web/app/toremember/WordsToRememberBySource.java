package com.github.bogdanovmn.translator.web.app.toremember;

import com.github.bogdanovmn.translator.web.orm.Source;
import com.github.bogdanovmn.translator.web.orm.WordRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class WordsToRememberBySource {
	private final List<WordRepository.WordBySourceWithUserProgress> wordsData;
	private final Source source;
	private final Long userCount;

	WordsToRememberBySource(List<WordRepository.WordBySourceWithUserProgress> wordsBySourceData, Source source, Long userCount) {
		this.wordsData = wordsBySourceData;
		this.source = source;
		this.userCount = userCount;
	}

	Map<String, Object> toView() {
		return new HashMap<String, Object>() {{
			put(
				"words",
				wordsData.stream()
					.map(x ->
						new Word(
							x.getWordSource().getWord().getId(),
							x.getWordSource().getWord().getName(),
							x.getWordSource().getCount(),
							x.getUserProgress() != null
						)
					)
					.collect(Collectors.toList())
			);
			put("source", source);
			put("userCount", userCount);
		}};
	}

	class Word {
		Integer id;
		String name;
		Integer count;
		boolean inProgress;

		Word(Integer id, String name, Integer count, boolean inProgress) {
			this.id = id;
			this.name = name;
			this.count = count;
			this.inProgress = inProgress;
		}

		Word getWord() {
			return this;
		}

		boolean getWordProgress() {
			return inProgress;
		}
	}
}
