package com.github.bogdanovmn.translator.web.app.source;

import com.github.bogdanovmn.translator.web.orm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SourcesService {
	private final static int WORDS_PER_PAGE = 10;

	private final SourceRepository sourceRepository;
	private final UserRememberedWordRepository userRememberedWordRepository;
	private final WordRepository wordRepository;

	@Autowired
	SourcesService(SourceRepository sourceRepository, UserRememberedWordRepository userRememberedWordRepository, WordRepository wordRepository) {
		this.sourceRepository = sourceRepository;
		this.userRememberedWordRepository = userRememberedWordRepository;
		this.wordRepository = wordRepository;
	}

	List<SourceRepository.WithUserStatistic> getAllWithUserStatistic(User user) {
		return sourceRepository.getAllWithUserStatistic(user.getId());
	}

	public long userRememberedWordsCount(int userId, int sourceId) {
		return userRememberedWordRepository.getCountBySource(
			userId,
			sourceId
		);
	}

	public Source get(int id) {
		return sourceRepository.getOne(id);
	}

	public List<WordRepository.WordBySourceWithUserProgress> getUnknownWordsBySource(User user, Integer sourceId) {
		return wordRepository.unknownBySource(
			user.getId(),
			sourceId,
			PageRequest.of(0, WORDS_PER_PAGE)
		);
	}
}
