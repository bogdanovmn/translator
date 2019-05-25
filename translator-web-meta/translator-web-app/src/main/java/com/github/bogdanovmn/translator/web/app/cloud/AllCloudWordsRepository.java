package com.github.bogdanovmn.translator.web.app.cloud;

import com.github.bogdanovmn.translator.web.orm.entity.WordRepository;

import java.util.List;

class AllCloudWordsRepository implements CloudWordsRepository {

	@Override
	public List<CloudWord> all(WordRepository wordRepository, Integer userId) {
		return new CloudWords(
			wordRepository.findAllByBlackListFalseAndFrequencyGreaterThanOrderByName(1)
		).words();
	}

	@Override
	public List<CloudWord> bySource(WordRepository wordRepository, Integer sourceId, Integer userId) {
		return new CloudWords(
			wordRepository.allBySourceForCloud(sourceId)
		).words();
	}
}
