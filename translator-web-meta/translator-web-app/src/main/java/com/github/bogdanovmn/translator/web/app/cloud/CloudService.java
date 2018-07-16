package com.github.bogdanovmn.translator.web.app.cloud;

import com.github.bogdanovmn.translator.web.orm.User;
import com.github.bogdanovmn.translator.web.orm.WordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class CloudService {
	private final WordRepository wordRepository;

	CloudService(final WordRepository wordRepository) {
		this.wordRepository = wordRepository;
	}

	List<CloudWord> allWords(CloudContentFilter filter, User user) {
		return filter.cloudWordsRepository().all(wordRepository, user.getId());
	}

	List<CloudWord> sourceWords(Integer sourceId, CloudContentFilter filter, User user) {
		return filter.cloudWordsRepository().bySource(wordRepository, sourceId, user.getId());
	}
}
