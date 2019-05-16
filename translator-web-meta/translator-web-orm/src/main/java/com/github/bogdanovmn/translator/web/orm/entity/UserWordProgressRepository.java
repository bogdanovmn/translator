package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntityRepository;

public interface UserWordProgressRepository extends BaseEntityRepository<UserWordProgress> {
	UserWordProgress findByUserAndWord(User user, Word word);

	void removeAllByWord(Word word);

}
