package com.github.bogdanovmn.translator.web.app.source;

import com.github.bogdanovmn.translator.web.orm.SourceRepository;
import com.github.bogdanovmn.translator.web.orm.SourceWithUserStatistic;
import com.github.bogdanovmn.translator.web.orm.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class SourcesService {
	private final SourceRepository sourceRepository;

	@Autowired
	SourcesService(SourceRepository sourceRepository) {
		this.sourceRepository = sourceRepository;
	}

	List<SourceWithUserStatistic> getAll(User user) {
		return this.sourceRepository.getAllWithUserStatistic(user.getId());
	}
}
