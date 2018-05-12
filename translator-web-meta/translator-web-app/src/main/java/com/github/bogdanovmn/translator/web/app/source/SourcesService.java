package com.github.bogdanovmn.translator.web.app.source;

import com.github.bogdanovmn.translator.web.orm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
class SourcesService {
	private static final Logger LOG = LoggerFactory.getLogger(SourcesService.class);

	private final SourceRepository sourceRepository;
	private final WordSourceRepository wordSourceRepository;

	@Autowired
	public SourcesService(SourceRepository sourceRepository, WordSourceRepository wordSourceRepository) {
		this.sourceRepository = sourceRepository;
		this.wordSourceRepository = wordSourceRepository;
	}

	List<SourceWithUserStatistic> getAll(User user) {
		return this.sourceRepository.getAllWithUserStatistic(user.getId());
	}

	@Transactional(rollbackFor = Exception.class)
	void delete(int sourceId) {
		LOG.info("Delete source with id = {}", sourceId);

		Source source = sourceRepository.getOne(sourceId);
		if (source == null) {
			throw new IllegalStateException("Source not exists");
		}

		Set<WordSource> wordSources = wordSourceRepository.findAllBySource(source);
		LOG.info("Prepare delete word links: {}", wordSources.size());
		for (WordSource ws : wordSources) {
			Word word = ws.getWord();
			int wordFreqBefore = word.getFrequence();
			int wordSourcesCountBefore = word.getSourcesCount();

			word.decFrequence(ws.getCount());
			word.decSourcesCount();

			LOG.debug(
				"Change word '{}' statistic: freq {} -> {}, sources {} -> {}",
					word.getName(), wordFreqBefore, word.getFrequence(), wordSourcesCountBefore, word.getSourcesCount()
			);

			wordSourceRepository.delete(ws);
		}
		LOG.info("Word links delete done");

		sourceRepository.delete(source);

		LOG.info("Delete is done");
	}
}
