package com.github.bogdanovmn.translator.web.app.source;

import com.github.bogdanovmn.translator.web.orm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class SourcesService {
	private static final Logger LOG = LoggerFactory.getLogger(SourcesService.class);

	private final SourceRepository sourceRepository;
	private final WordSourceRepository wordSourceRepository;
	private final WordRepository wordRepository;

	@Autowired
	SourcesService(SourceRepository sourceRepository, WordSourceRepository wordSourceRepository, WordRepository wordRepository) {
		this.sourceRepository = sourceRepository;
		this.wordSourceRepository = wordSourceRepository;
		this.wordRepository = wordRepository;
	}

	List<SourceWithUserStatistic> getAll(User user) {
		return this.sourceRepository.getAllWithUserStatistic(user.getId());
	}

	@Transactional(rollbackFor = Exception.class)
	public synchronized void delete(int sourceId) {
		LOG.info("Delete source with id = {}", sourceId);

		Source source = sourceRepository.getOne(sourceId);
		if (source == null) {
			throw new IllegalStateException("Source not exists");
		}

		LOG.info("Delete word links");
		wordSourceRepository.deleteAllBySource(source);

		sourceRepository.delete(source);

		LOG.info("Update Word statistic");
		wordRepository.updateStatistic();

		LOG.info("Delete is done");
	}
}
