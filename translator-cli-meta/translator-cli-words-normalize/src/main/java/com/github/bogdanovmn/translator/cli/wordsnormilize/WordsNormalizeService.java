package com.github.bogdanovmn.translator.cli.wordsnormilize;

import com.github.bogdanovmn.translator.core.NormalizedWords;
import com.github.bogdanovmn.translator.web.orm.Word;
import com.github.bogdanovmn.translator.web.orm.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
class WordsNormalizeService {
	@Autowired
	private WordRepository wordRepository;

	void dry() {
		Set<Word> words = this.wordRepository.getAllByBlackListFalse();
		NormalizedWords normalizedWords = new NormalizedWords(
			words.stream()
				.map(Word::getName)
				.collect(Collectors.toList())
		);

		normalizedWords.printWordsWithForms();
	}
}
