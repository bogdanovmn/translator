package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntityRepository;

import java.util.List;
import java.util.Set;

public interface WordSourceRepository extends BaseEntityRepository<WordSource> {
	List<WordSource> toRemember(Integer userId, Integer sourceId, Integer popularCount, Integer rareCount);
	Set<WordSource> findAllByWord(Word word);

	void deleteAllBySource(Source source);
}
