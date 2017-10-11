package com.github.bogdanovmn.translator.web.orm.repository.domain;

import com.github.bogdanovmn.translator.web.orm.entity.domain.Word;
import com.github.bogdanovmn.translator.web.orm.repository.common.BaseEntityWithUniqueNameRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Set;

public interface WordRepository extends BaseEntityWithUniqueNameRepository<Word> {
	@EntityGraph(value = "wordsWithTranslate")
	Set<Word> findTop10ByBlackListFalseOrderBySourcesCountDescFrequenceDesc();
	@EntityGraph(value = "wordsWithTranslate")
	Set<Word> findAllByBlackListFalseOrderBySourcesCountDescFrequenceDesc();
}
