package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.web.orm.Source;
import com.github.bogdanovmn.translator.web.orm.WordSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class WordsToRememberBySource {
	private final List<WordSource> wordSources;
	private final Source source;
	private final Long userCount;

	WordsToRememberBySource(List<WordSource> wordSources, Source source, Long userCount) {
		this.wordSources = wordSources;
		this.source = source;
		this.userCount = userCount;
	}

	Map<String, Object> toView() {
		return new HashMap<String, Object>() {{
			put(
				"words",
				wordSources.stream()
					.map(x ->
						new Word(
							x.getWord().getId(),
							x.getWord().getName(),
							x.getCount()
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

		Word(Integer id, String name, Integer count) {
			this.id = id;
			this.name = name;
			this.count = count;
		}
	}
}
