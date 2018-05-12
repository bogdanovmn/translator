package com.github.bogdanovmn.translator.web.app.admin;

import com.github.bogdanovmn.translator.web.orm.Word;
import com.github.bogdanovmn.translator.web.orm.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class WordAdminService {
	private final WordRepository wordRepository;

	@Autowired
	WordAdminService(WordRepository wordRepository) {
		this.wordRepository = wordRepository;
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
