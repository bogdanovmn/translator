package com.github.bogdanovmn.translator.web.app.words;

import com.github.bogdanovmn.translator.core.HttpServiceException;
import com.github.bogdanovmn.translator.core.ResponseNotFoundException;
import com.github.bogdanovmn.translator.core.translate.TranslateService;
import com.github.bogdanovmn.translator.web.app.infrastructure.config.security.TranslateSecurityService;
import com.github.bogdanovmn.translator.web.orm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

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
	@Autowired
	private TranslateService translateService;
	@Autowired
	private EntityFactory entityFactory;
	@Autowired
	private TranslateRepository translateRepository;

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

	String translateWord(Integer wordId) throws HttpServiceException {
		Word word = wordRepository.findById(wordId)
			.orElseThrow(() ->
				new RuntimeException(
					String.format("Unknown word (id = %d", wordId)
				)
			);

		TranslateProvider provider = (TranslateProvider) entityFactory.getPersistBaseEntityWithUniqueName(
			new TranslateProvider("Google")
		);

		Set<String> translates;
		try {
			translates = translateService.translate(word.getName());
			translates.forEach(x ->
				translateRepository.save(
					new Translate()
						.setProvider(provider)
						.setWord(word)
						.setValue(x)
				)
			);
		}
		catch (ResponseNotFoundException e) {
			translateRepository.save(
				new Translate()
					.setProvider(provider)
					.setWord(word)
					.setValue(null)
			);
			throw new ResponseNotFoundException(e.getMessage());
		}

		return String.join(", ", translates);
	}
}
