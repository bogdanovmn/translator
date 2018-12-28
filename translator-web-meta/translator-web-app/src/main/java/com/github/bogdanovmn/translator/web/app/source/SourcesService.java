package com.github.bogdanovmn.translator.web.app.source;

import com.github.bogdanovmn.translator.web.orm.Source;
import com.github.bogdanovmn.translator.web.orm.SourceRepository;
import com.github.bogdanovmn.translator.web.orm.User;
import com.github.bogdanovmn.translator.web.orm.UserRememberedWordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SourcesService {
	private final SourceRepository sourceRepository;
	private final UserRememberedWordRepository userRememberedWordRepository;

	SourcesService(SourceRepository sourceRepository, UserRememberedWordRepository userRememberedWordRepository) {
		this.sourceRepository = sourceRepository;
		this.userRememberedWordRepository = userRememberedWordRepository;
	}

	List<SourceRepository.WithUserStatistic> getAllWithUserStatistic(User user) {
		return this.sourceRepository.getAllWithUserStatistic(user.getId());
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
}
