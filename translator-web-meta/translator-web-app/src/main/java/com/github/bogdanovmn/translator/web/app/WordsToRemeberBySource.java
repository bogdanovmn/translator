package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.web.orm.Source;
import com.github.bogdanovmn.translator.web.orm.Word;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class WordsToRemeberBySource {
	private final List<Word> words;
	private final Source source;

	WordsToRemeberBySource(List<Word> words, Source source) {
		this.words = words;
		this.source = source;
	}

	Map<String, Object> toView() {
		return new HashMap<String, Object>() {{
			put("words", words);
			put("source", source);
		}};
	}
}
