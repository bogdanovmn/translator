package com.github.bogdanovmn.translator.web.app.cloud;

import com.github.bogdanovmn.translator.web.orm.BaseEntityWithUniqueName;
import com.github.bogdanovmn.translator.web.orm.Word;
import com.github.bogdanovmn.translator.web.orm.WordRepository;
import com.github.bogdanovmn.translator.web.orm.WordSourceRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
class CloudService {
	private static final int MIN_SIZE_PERCENT = 55;
	private static final int MAX_SIZE_PERCENT = 415;

	private final WordRepository wordRepository;
	private final WordSourceRepository wordSourceRepository;

	CloudService(final WordRepository wordRepository, final WordSourceRepository wordSourceRepository) {
		this.wordRepository = wordRepository;
		this.wordSourceRepository = wordSourceRepository;
	}

	List<CloudWord> allWords() {
		List<Word> words = wordRepository.findAllByBlackListFalseAndFrequenceGreaterThanOrderByName(1);

		return cloudWords(words);
	}

	List<CloudWord> sourceWords(Integer sourceId) {
		List<Word> words = wordSourceRepository.findAllBySourceId(sourceId).stream()
			.filter(x -> !x.getWord().isBlackList() && x.getCount() > 1)
			.map(x ->
				new Word(
					x.getWord().getName()
				)
				.setFrequence(x.getCount())
			)
			.sorted(Comparator.comparing(BaseEntityWithUniqueName::getName))
			.collect(Collectors.toList());

		return cloudWords(words);
	}

	private List<CloudWord> cloudWords(final List<Word> words) {
		final int wordMaxFreq = words.stream().mapToInt(Word::getFrequence).max().orElse(0);

		return words.stream()
			.filter(word -> word.getFrequence() * 100 / wordMaxFreq > 1)
			.map(
				word -> new CloudWord(
					word,
					word.getFrequence() * (MAX_SIZE_PERCENT - MIN_SIZE_PERCENT) / wordMaxFreq + MIN_SIZE_PERCENT
				)
			)
			.collect(Collectors.toList());
	}
}
