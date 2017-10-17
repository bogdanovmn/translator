package com.github.bogdanovmn.translator.web.orm.repository.domain;

import com.github.bogdanovmn.translator.web.orm.entity.domain.Word;
import com.github.bogdanovmn.translator.web.orm.repository.common.BaseEntityWithUniqueNameRepository;

public interface WordRepository extends BaseEntityWithUniqueNameRepository<Word> {
	Integer countByBlackListFalse();
}
