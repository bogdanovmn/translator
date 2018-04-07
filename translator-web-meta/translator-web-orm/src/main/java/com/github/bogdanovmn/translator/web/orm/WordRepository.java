package com.github.bogdanovmn.translator.web.orm;

import java.util.List;
import java.util.Set;

public interface WordRepository extends BaseEntityWithUniqueNameRepository<Word> {
	Integer countByBlackListFalse();
	Set<Word> getAllByBlackListFalse();

	List<Word> toRemember(Integer userId);
}
