package com.github.bogdanovmn.translator.web.app.cloud;

import com.github.bogdanovmn.translator.web.orm.WordRepository;

import java.util.List;

class UnknownCloudWordsRepository implements CloudWordsRepository {

	@Override
	public List<CloudWord> all(WordRepository wordRepository, Integer userId) {
		return new CloudWords(
			wordRepository.allUnknownForCloud(userId)
		).words();
	}

	@Override
	public List<CloudWord> bySource(WordRepository wordRepository, Integer sourceId, Integer userId) {
		return new CloudWords(
			wordRepository.allUnknownBySourceForCloud(userId, sourceId)
		).words();
	}
}
