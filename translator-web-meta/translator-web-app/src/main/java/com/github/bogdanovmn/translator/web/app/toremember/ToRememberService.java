package com.github.bogdanovmn.translator.web.app.toremember;

import com.github.bogdanovmn.translator.core.TranslateService;
import com.github.bogdanovmn.translator.core.TranslateServiceException;
import com.github.bogdanovmn.translator.core.TranslateServiceUnknownWordException;
import com.github.bogdanovmn.translator.web.app.infrastructure.config.security.TranslateSecurityService;
import com.github.bogdanovmn.translator.web.orm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
class ToRememberService {
	@Autowired
	private TranslateSecurityService securityService;
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
	@Autowired
	private SourceRepository sourceRepository;
	@Autowired
	private WordSourceRepository wordSourceRepository;

	private User getUser() {
		return securityService.getLoggedInUser();
	}

	List<WordRepository.WordWithUserProgress> getAll() {
		return wordRepository.unknownByAllSources(
			getUser().getId(),
			PageRequest.of(0, 20)
		);
	}

	List<WordRepository.WordBySourceWithUserProgress> getAllBySource(Integer sourceId) {
		return wordRepository.unknownBySource(
			getUser().getId(),
			sourceId,
			PageRequest.of(0, 20)
		);
	}

	void rememberWord(Integer wordId) {
		if (null == userRememberedWordRepository.findFirstByUserAndWordId(getUser(), wordId)) {
			userRememberedWordRepository.save(
				new UserRememberedWord()
					.setUser(getUser())
					.setWord(new Word(wordId))
					.setUpdated(new Date())
			);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void holdOverWord(Integer wordId) {
		if (null == userHoldOverWordRepository.findFirstByUserAndWordId(getUser(), wordId)) {
			Word word = new Word(wordId);
			userHoldOverWordRepository.save(
				new UserHoldOverWord()
					.setUser(getUser())
					.setWord(word)
			);
			UserWordProgress progress = userWordProgressRepository.findByUserAndWord(getUser(), word);
			if (null == progress) {
				progress = new UserWordProgress()
					.setUser(getUser())
					.setWord(word);
			}
			progress.incHoldOverCount();
			userWordProgressRepository.save(progress);
		}
	}

	String translateWord(Integer wordId) throws TranslateServiceException, IOException {
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
		catch (TranslateServiceUnknownWordException e) {
			translateRepository.save(
				new Translate()
					.setProvider(provider)
					.setWord(word)
					.setValue(null)
			);
			throw new TranslateServiceUnknownWordException(e.getMessage());
		}

		return String.join(", ", translates);
	}
}
