package com.github.bogdanovmn.translator.web.app.toremember;

import com.github.bogdanovmn.translator.core.TranslateService;
import com.github.bogdanovmn.translator.core.TranslateServiceException;
import com.github.bogdanovmn.translator.core.TranslateServiceUnknownWordException;
import com.github.bogdanovmn.translator.web.app.infrastructure.config.security.TranslateSecurityService;
import com.github.bogdanovmn.translator.web.orm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class ToRememberService {
	@Autowired
	private TranslateSecurityService securityService;
	@Autowired
	private UserRememberedWordRepository userRememberedWordRepository;
	@Autowired
	private UserHoldOverWordRepository userHoldOverWordRepository;
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

	List<Word> getAll() {
		return wordRepository.toRemember(getUser().getId());
	}

	WordsToRememberBySource getAllBySource(Integer sourceId) {
		Source source = sourceRepository.getOne(sourceId);
		List<WordSource> wordSources = wordSourceRepository.toRemember(
			getUser().getId(), source.getId()
		);

		return new WordsToRememberBySource(
			wordSources,
			source,
			userRememberedWordRepository.getCountBySource(getUser().getId(), sourceId)
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

	void holdOverWord(Integer wordId) {
		if (null == userHoldOverWordRepository.findFirstByUserAndWordId(getUser(), wordId)) {
			userHoldOverWordRepository.save(
				new UserHoldOverWord()
					.setUser(getUser())
					.setWord(new Word(wordId))
					.setUpdated(new Date())
			);
		}
	}

	String translateWord(Integer wordId) throws TranslateServiceException, IOException {
		Word word = wordRepository.findOne(wordId);

		if (word == null) {
			throw new RuntimeException(
				String.format("Unknown word (id = %d", wordId)
			);
		}

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

		return translates.stream().collect(Collectors.joining(", "));
	}

	void blackListWord(Integer wordId) {
		Word word = wordRepository.findOne(wordId);
		if (word != null) {
			wordRepository.save(
				word.setBlackList(true)
			);
		}
	}
}
