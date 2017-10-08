package com.github.bogdanovmn.translator.web.orm.repository.domain;

import com.github.bogdanovmn.translator.web.orm.entity.domain.Word;
import com.github.bogdanovmn.translator.web.orm.repository.common.BaseEntityWithUniqueNameRepository;

import java.util.Set;

public interface WordRepository extends BaseEntityWithUniqueNameRepository<Word> {
	Set<Word> findAllByBlackListFalse();
}
