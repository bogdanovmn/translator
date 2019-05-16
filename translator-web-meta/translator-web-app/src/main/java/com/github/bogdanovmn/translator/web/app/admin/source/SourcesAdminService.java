package com.github.bogdanovmn.translator.web.app.admin.source;

import com.github.bogdanovmn.translator.web.orm.entity.*;
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
	private final ProperNameRepository properNameRepository;
	private final ProperNameSourceRepository properNameSourceRepository;

	@Autowired
	SourcesAdminService(SourceRepository sourceRepository, WordSourceRepository wordSourceRepository, WordRepository wordRepository, ProperNameRepository properNameRepository, ProperNameSourceRepository properNameSourceRepository) {
		this.sourceRepository = sourceRepository;
		this.wordSourceRepository = wordSourceRepository;
		this.wordRepository = wordRepository;
		this.properNameRepository = properNameRepository;
		this.properNameSourceRepository = properNameSourceRepository;
	}

	@Transactional(rollbackFor = Exception.class)
	public synchronized void delete(int sourceId) {
		LOG.info("Delete source with id = {}", sourceId);

		Source source = sourceRepository.findById(sourceId)
			.orElseThrow(() -> new IllegalStateException("Source not exists"));

		LOG.info("Delete word links");
		wordSourceRepository.deleteAllBySource(source);
		LOG.info("Remove proper names");
		properNameSourceRepository.deleteAllBySource(source);
		sourceRepository.delete(source);

		LOG.info("Update Word statistic");
		wordSourceRepository.flush();
		wordRepository.updateStatistic();

		LOG.info("Remove unused words");
		wordRepository.removeUnused();

		LOG.info("Remove unused proper names");
		properNameRepository.removeUnused();

		LOG.info("Deletion is done");
	}
}
