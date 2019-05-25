package com.github.bogdanovmn.translator.web.app.cloud;

import com.github.bogdanovmn.translator.web.orm.entity.ProperNameRepository;
import com.github.bogdanovmn.translator.web.orm.entity.User;
import com.github.bogdanovmn.translator.web.orm.entity.Word;
import com.github.bogdanovmn.translator.web.orm.entity.WordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class CloudService {
	private final WordRepository wordRepository;
	private final ProperNameRepository properNameRepository;

	CloudService(final WordRepository wordRepository, ProperNameRepository properNameRepository) {
		this.wordRepository = wordRepository;
		this.properNameRepository = properNameRepository;
	}

	List<CloudWord> allWords(CloudContentFilter filter, User user) {
		return filter.cloudWordsRepository().all(wordRepository, user.getId());
	}

	List<CloudWord> sourceWords(Integer sourceId, CloudContentFilter filter, User user) {
		return filter.cloudWordsRepository().bySource(wordRepository, sourceId, user.getId());
	}

	List<CloudWord> sourceProperNames(Integer sourceId) {
		return new CloudWords(
			properNameRepository.properNameWordsBySourceId(sourceId).stream()
				.map(x -> new Word(x.getName()).setFrequency(x.getCount()))
				.collect(Collectors.toList())
		).words();
	}
}
