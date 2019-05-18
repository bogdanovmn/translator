package com.github.bogdanovmn.translator.web.app.admin.word;

import com.github.bogdanovmn.translator.web.orm.entity.SourceRepository;
import com.github.bogdanovmn.translator.web.orm.entity.UserHoldOverWordRepository;
import com.github.bogdanovmn.translator.web.orm.entity.Word;
import com.github.bogdanovmn.translator.web.orm.entity.WordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
class WordAdminService {
	private final WordRepository wordRepository;
	private final UserHoldOverWordRepository holdOverWordRepository;
	private final SourceRepository sourceRepository;

	@Autowired
	WordAdminService(WordRepository wordRepository, UserHoldOverWordRepository holdOverWordRepository, SourceRepository sourceRepository) {
		this.wordRepository = wordRepository;
		this.holdOverWordRepository = holdOverWordRepository;
		this.sourceRepository = sourceRepository;
	}

	@Transactional(rollbackFor = Exception.class)
	public void blackListWord(Integer wordId) {
		Word word = wordRepository.findById(wordId)
			.orElseThrow(() ->
				new RuntimeException(
					String.format("Unknown word (id = %d", wordId)
				));
		wordRepository.save(
			word.setBlackList(true)
		);
		LOG.info("Update all sources black list value");
		sourceRepository.updateBlackListValue();
	}


	@Scheduled(fixedDelay = 3 * 3600 * 1000, initialDelay = 5 * 1000)
	void updateHoldOverList() {
		LocalDateTime expireDate = LocalDateTime.now().minus(1, ChronoUnit.DAYS);
		LOG.info("Remove all hold over records updated before {}", expireDate);
		holdOverWordRepository.deleteAllByUpdatedBefore(expireDate);
		LOG.info("Success");
	}
}
