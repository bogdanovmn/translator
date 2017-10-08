package com.github.bogdanovmn.translator.web.app.service;

import com.github.bogdanovmn.translator.web.app.config.security.TranslateSecurityService;
import com.github.bogdanovmn.translator.web.orm.entity.app.User;
import com.github.bogdanovmn.translator.web.orm.entity.app.UserHoldOverWord;
import com.github.bogdanovmn.translator.web.orm.entity.app.UserRememberedWord;
import com.github.bogdanovmn.translator.web.orm.entity.domain.Word;
import com.github.bogdanovmn.translator.web.orm.repository.app.UserHoldOverWordRepository;
import com.github.bogdanovmn.translator.web.orm.repository.app.UserRememberedWordRepository;
import com.github.bogdanovmn.translator.web.orm.repository.domain.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	private User getUser() {
		return securityService.getLoggedInUser();
	}

	public List<Word> getAll() {
		Set<Word> userRememberedWords = this.userRememberedWordRepository
			.findAllByUser(this.getUser())
				.stream()
					.map(UserRememberedWord::getWord)
					.collect(Collectors.toSet());

		Set<Word> userHoldOverWords = this.userHoldOverWordRepository
			.findAllByUser(this.getUser())
				.stream()
					.map(UserHoldOverWord::getWord)
					.collect(Collectors.toSet());

		return this.wordRepository.findAllByBlackListFalse().stream()
			.filter(x -> !userRememberedWords.contains(x) && !userHoldOverWords.contains(x))
			.collect(Collectors.toList());
	}

	public void rememberWord(Integer wordId) {
		if (null == this.userRememberedWordRepository.findFirstByUserAndWordId(this.getUser(), wordId)) {
			this.userRememberedWordRepository.save(
				new UserRememberedWord()
					.setUser(this.getUser())
					.setWord((Word) new Word().setId(wordId))
					.setUpdated(new Date())
			);
		}
	}

	public void holdOverWord(Integer wordId) {
		if (null == this.userHoldOverWordRepository.findFirstByUserAndWordId(this.getUser(), wordId)) {
			this.userHoldOverWordRepository.save(
				new UserHoldOverWord()
					.setUser(this.getUser())
					.setWord((Word) new Word().setId(wordId))
					.setUpdated(new Date())
			);
		}
	}

	public void translateWord(Integer wordId) {

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
