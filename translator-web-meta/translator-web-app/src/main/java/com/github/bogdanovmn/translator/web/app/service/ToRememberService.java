package com.github.bogdanovmn.translator.web.app.service;

import com.github.bogdanovmn.translator.web.app.config.security.TranslateSecurityService;
import com.github.bogdanovmn.translator.web.orm.entity.app.User;
import com.github.bogdanovmn.translator.web.orm.entity.app.UserRememberedWord;
import com.github.bogdanovmn.translator.web.orm.entity.domain.Word;
import com.github.bogdanovmn.translator.web.orm.repository.domain.UserRememberedWordRepository;
import com.github.bogdanovmn.translator.web.orm.repository.domain.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
	private WordRepository wordRepository;

	private User getUser() {
		return securityService.getLoggedInUser();
	}

	public List<Word> getAll() {
		List<Word> result = new ArrayList<>();

		Set<Word> userWords = this.userRememberedWordRepository
			.getUserRememberedWordsByUser(this.getUser())
				.stream()
					.map(UserRememberedWord::getWord)
					.collect(Collectors.toSet());

		this.wordRepository.findAllByHiddenFalse().forEach(
			word -> {
				if (!userWords.contains(word)) {
					result.add(word);
				}
			}
		);

		return result;
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

	public void translateWord(Integer wordId) {

	}

	public void holdOverWord(Integer wordId) {

	}

	public void blackListWord(Integer wordId) {

	}
}
