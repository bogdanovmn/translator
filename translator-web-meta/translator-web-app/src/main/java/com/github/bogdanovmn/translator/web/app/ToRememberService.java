package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.core.TranslateService;
import com.github.bogdanovmn.translator.core.TranslateServiceException;
import com.github.bogdanovmn.translator.core.TranslateServiceUnknownWordException;
import com.github.bogdanovmn.translator.web.app.config.security.TranslateSecurityService;
import com.github.bogdanovmn.translator.web.orm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ToRememberService {
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
	private UserRepository userRepository;

	private User getUser() {
		return securityService.getLoggedInUser();
	}

	public List<Word> getAll() {
		return this.userRepository.getWordsToRemember(this.getUser().getId());
	}

	public void rememberWord(Integer wordId) {
		if (null == this.userRememberedWordRepository.findFirstByUserAndWordId(this.getUser(), wordId)) {
			this.userRememberedWordRepository.save(
				new UserRememberedWord()
					.setUser(this.getUser())
					.setWord(new Word(wordId))
					.setUpdated(new Date())
			);
		}
	}

	public void holdOverWord(Integer wordId) {
		if (null == this.userHoldOverWordRepository.findFirstByUserAndWordId(this.getUser(), wordId)) {
			this.userHoldOverWordRepository.save(
				new UserHoldOverWord()
					.setUser(this.getUser())
					.setWord(new Word(wordId))
					.setUpdated(new Date())
			);
		}
	}

	public String translateWord(Integer wordId) throws TranslateServiceException, IOException {
		Word word = this.wordRepository.findOne(wordId);

		if (word == null) {
			throw new RuntimeException(
				String.format("Unknown word (id = %d", wordId)
			);
		}

		TranslateProvider provider = (TranslateProvider) this.entityFactory.getPersistBaseEntityWithUniqueName(
			new TranslateProvider("Google")
		);

		Set<String> translates;
		try {
			translates = translateService.translate(word.getName());
			translates.forEach(x ->
				this.translateRepository.save(
					new Translate()
						.setProvider(provider)
						.setWord(word)
						.setValue(x)
				)
			);
		}
		catch (TranslateServiceUnknownWordException e) {
			this.translateRepository.save(
				new Translate()
					.setProvider(provider)
					.setWord(word)
					.setValue(null)
			);
			throw new TranslateServiceUnknownWordException(e.getMessage());
		}

		return translates.stream().collect(Collectors.joining(", "));
	}

	public void blackListWord(Integer wordId) {
		Word word = this.wordRepository.findOne(wordId);
		if (word != null) {
			this.wordRepository.save(
				word.setBlackList(true)
			);
		}
	}
}
