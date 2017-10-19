package com.github.bogdanovmn.translator.web.orm;

public interface WordRepository extends BaseEntityWithUniqueNameRepository<Word> {
	Integer countByBlackListFalse();
}
