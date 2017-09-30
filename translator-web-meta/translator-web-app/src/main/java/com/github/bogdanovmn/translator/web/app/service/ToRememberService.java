package com.github.bogdanovmn.translator.web.app.service;

import com.github.bogdanovmn.translator.web.orm.entity.app.User;
import com.github.bogdanovmn.translator.web.orm.entity.app.UserRememberedWord;
import com.github.bogdanovmn.translator.web.orm.entity.domain.Word;
import com.github.bogdanovmn.translator.web.orm.repository.domain.UserRememberedWordRepository;
import com.github.bogdanovmn.translator.web.orm.repository.domain.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ToRememberService {
	@Autowired
	private UserRememberedWordRepository userRememberedWordRepository;
	@Autowired
	private WordRepository wordRepository;

	public List<Word> getAll(User user) {
		List<Word> result = new ArrayList<>();

		Set<Word> userWords = this.userRememberedWordRepository.getUserRememberedWordsByUser(user).stream()
			.map(UserRememberedWord::getWord)
			.collect(Collectors.toSet());

		this.wordRepository.findAll().forEach(
			word -> {
				if (userWords.contains(word)) {
					result.add(word);
				}
			}
		);

		return result;
	}
}
