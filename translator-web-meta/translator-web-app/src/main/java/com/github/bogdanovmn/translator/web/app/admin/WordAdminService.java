package com.github.bogdanovmn.translator.web.app.admin;

import com.github.bogdanovmn.translator.web.orm.Word;
import com.github.bogdanovmn.translator.web.orm.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordAdminService {
	private final WordRepository wordRepository;

	@Autowired
	public WordAdminService(WordRepository wordRepository) {
		this.wordRepository = wordRepository;
	}

	void blackListWord(Integer wordId) {
		Word word = this.wordRepository.findOne(wordId);
		if (word != null) {
			this.wordRepository.save(
				word.setBlackList(true)
			);
		}
	}
}
