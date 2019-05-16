package com.github.bogdanovmn.translator.web.app.admin.definition;

import com.github.bogdanovmn.translator.web.orm.entity.WordDefinitionServiceLog;
import com.github.bogdanovmn.translator.web.orm.entity.WordDefinitionServiceLogRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class DefinitionLogService {
	private final WordDefinitionServiceLogRepository logRepository;

	DefinitionLogService(WordDefinitionServiceLogRepository logRepository) {
		this.logRepository = logRepository;
	}

	List<WordDefinitionServiceLogRepository.StatusStatistic> statistic() {
		return logRepository.statusStatistic();
	}

	List<WordDefinitionServiceLog> lastEntries() {
		return logRepository.findAllByOrderByUpdatedDesc(
			PageRequest.of(0, 100)
		);
	}

	List<WordDefinitionServiceLog> entriesByStatus(WordDefinitionServiceLog.Status status) {
		return logRepository.findAllByStatusOrderByUpdatedDesc(status, PageRequest.of(0, 1000));
	}
}
