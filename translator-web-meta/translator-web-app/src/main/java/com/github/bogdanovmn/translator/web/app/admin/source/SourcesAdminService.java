package com.github.bogdanovmn.translator.web.app.admin.source;

import com.github.bogdanovmn.translator.web.orm.Source;
import com.github.bogdanovmn.translator.web.orm.SourceRepository;
import com.github.bogdanovmn.translator.web.orm.WordRepository;
import com.github.bogdanovmn.translator.web.orm.WordSourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
class SourcesAdminService {
	private final SourceRepository sourceRepository;
	private final WordSourceRepository wordSourceRepository;
	private final WordRepository wordRepository;

	@Autowired
	SourcesAdminService(SourceRepository sourceRepository, WordSourceRepository wordSourceRepository, WordRepository wordRepository) {
		this.sourceRepository = sourceRepository;
		this.wordSourceRepository = wordSourceRepository;
		this.wordRepository = wordRepository;
	}

	@Transactional(rollbackFor = Exception.class)
	public synchronized void delete(int sourceId) {
		LOG.info("Delete source with id = {}", sourceId);

		Source source = sourceRepository.findById(sourceId)
			.orElseThrow(() -> new IllegalStateException("Source not exists"));

		LOG.info("Delete word links");
		wordSourceRepository.deleteAllBySource(source);
		sourceRepository.delete(source);

		LOG.info("Update Word statistic");
		wordSourceRepository.flush();
		wordRepository.updateStatistic();

		LOG.info("Remove unused words");
		wordRepository.removeUnused();

		LOG.info("Deletion is done");
	}
}
