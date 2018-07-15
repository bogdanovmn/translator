package com.github.bogdanovmn.translator.web.app.cloud;

import com.github.bogdanovmn.translator.web.orm.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
class CloudService {
	private static final int MIN_SIZE_PERCENT = 45;
	private static final int MAX_SIZE_PERCENT = 515;

	private final WordRepository wordRepository;
	private final WordSourceRepository wordSourceRepository;

	CloudService(final WordRepository wordRepository, WordSourceRepository wordSourceRepository) {
		this.wordRepository = wordRepository;
		this.wordSourceRepository = wordSourceRepository;
	}

	List<CloudWord> allWords(CloudContentFilter cloudContentFilter, User user) {
		List<Word> words = new ArrayList<>();
		switch (cloudContentFilter.activeToggle()) {
			case ALL:
				words = wordRepository.findAllByBlackListFalseAndFrequenceGreaterThanOrderByName(1);
				break;
			case UNKNOWN:
				words = wordRepository.allUnknownWordsByUserForCloud(user.getId());
				break;
			case KNOWN:
				words = wordRepository.allKnownWordsByUserForCloud(user.getId());
				break;
		}
		return cloudWords(words);
	}

	List<CloudWord> sourceWords(Integer sourceId, CloudContentFilter filter, User user) {
		List<Word> words = new ArrayList<>();
		switch (filter.activeToggle()) {
			case ALL:
				words = wordSourceRepository.findAllBySourceId(sourceId).stream()
					.filter(x -> !x.getWord().isBlackList() && x.getCount() > 1)
					.map(x ->
						new Word(
							x.getWord().getName()
						)
							.setFrequence(x.getCount())
					)
					.sorted(Comparator.comparing(BaseEntityWithUniqueName::getName))
					.collect(Collectors.toList());
				break;
			case UNKNOWN:
				words = wordRepository.allUnknownWordsByUserAndSourceForCloud(user.getId(), sourceId);
				break;
			case KNOWN:
				words = wordRepository.allKnownWordsByUserAndSourceForCloud(user.getId(), sourceId);
				break;
		}

		return cloudWords(words);
	}

	private List<CloudWord> cloudWords(List<Word> words) {
		final int wordMaxFreq = words.stream().mapToInt(Word::getFrequence).max().orElse(0);

		return words.stream()
			.filter(word -> word.getFrequence() * 100 / wordMaxFreq > 0)
			.map(
				word -> new CloudWord(
					word,
					word.getFrequence() * (MAX_SIZE_PERCENT - MIN_SIZE_PERCENT) / wordMaxFreq + MIN_SIZE_PERCENT
				)
			)
			.collect(Collectors.toList());
	}
}
