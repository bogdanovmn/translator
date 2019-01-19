package com.github.bogdanovmn.translator.web.app.admin.word;

import com.github.bogdanovmn.translator.web.orm.UserHoldOverWordRepository;
import com.github.bogdanovmn.translator.web.orm.Word;
import com.github.bogdanovmn.translator.web.orm.WordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
class WordAdminService {
	private final WordRepository wordRepository;
	private final UserHoldOverWordRepository holdOverWordRepository;

	@Autowired
	WordAdminService(WordRepository wordRepository, UserHoldOverWordRepository holdOverWordRepository) {
		this.wordRepository = wordRepository;
		this.holdOverWordRepository = holdOverWordRepository;
	}

	void blackListWord(Integer wordId) {
		Word word = wordRepository.findById(wordId)
			.orElseThrow(() ->
				new RuntimeException(
					String.format("Unknown word (id = %d", wordId)
				));
		wordRepository.save(
			word.setBlackList(true)
		);
	}

	@Scheduled(fixedDelay = 12 * 3600 * 1000)
	void updateHoldOverList() {
		LocalDateTime expireDate = LocalDateTime.now().minus(2, ChronoUnit.DAYS);
		LOG.info("Remove all hold over records updated before {}", expireDate);
		holdOverWordRepository.deleteAllByUpdatedBefore(expireDate);
		LOG.info("Success");
	}

	@Scheduled(fixedDelay = 3600 * 1000)
	void fetchDefinitions() {

	}
}
