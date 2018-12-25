package com.github.bogdanovmn.translator.web.app.source;

import com.github.bogdanovmn.translator.web.orm.SourceRepository;
import com.github.bogdanovmn.translator.web.orm.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class SourcesService {
	private final SourceRepository sourceRepository;

	SourcesService(SourceRepository sourceRepository) {
		this.sourceRepository = sourceRepository;
	}

	List<SourceRepository.WithUserStatistic> getAllWithUserStatistic(User user) {
		return this.sourceRepository.getAllWithUserStatistic(user.getId());
	}
}
