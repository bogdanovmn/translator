package com.github.bogdanovmn.translator.web.app.words;

import com.github.bogdanovmn.translator.web.orm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
class UnknownWordsService {
	private final static int WORDS_PER_PAGE = 10;
	@Autowired
	private UserRememberedWordRepository userRememberedWordRepository;
	@Autowired
	private UserHoldOverWordRepository userHoldOverWordRepository;
	@Autowired
	private UserWordProgressRepository userWordProgressRepository;
	@Autowired
	private WordRepository wordRepository;

	List<WordRepository.WordWithUserProgress> getAll(User user) {
		return wordRepository.unknownByAllSources(
			user.getId(),
			PageRequest.of(0, WORDS_PER_PAGE)
		);
	}

	void rememberWord(User user, Integer wordId) {
		if (null == userRememberedWordRepository.findFirstByUserAndWordId(user, wordId)) {
			userRememberedWordRepository.save(
				new UserRememberedWord()
					.setUser(user)
					.setWord(new Word(wordId))
					.setUpdated(new Date())
			);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void holdOverWord(User user, Integer wordId) {
		if (null == userHoldOverWordRepository.findFirstByUserAndWordId(user, wordId)) {
			Word word = new Word(wordId);
			userHoldOverWordRepository.save(
				new UserHoldOverWord()
					.setUser(user)
					.setWord(word)
			);
			UserWordProgress progress = userWordProgressRepository.findByUserAndWord(user, word);
			if (null == progress) {
				progress = new UserWordProgress()
					.setUser(user)
					.setWord(word);
			}
			progress.incHoldOverCount();
			userWordProgressRepository.save(progress);
		}
	}
}
