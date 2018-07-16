package com.github.bogdanovmn.translator.web.app.cloud;

import com.github.bogdanovmn.translator.web.orm.WordRepository;

import java.util.List;

interface CloudWordsRepository {
	List<CloudWord> all(WordRepository wordRepository, Integer userId);

	List<CloudWord> bySource(WordRepository wordRepository, Integer sourceId, Integer userId);
}
