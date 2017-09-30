package com.github.bogdanovmn.translator.web.orm.repository.domain;

import com.github.bogdanovmn.translator.web.orm.entity.app.User;
import com.github.bogdanovmn.translator.web.orm.entity.app.UserRememberedWord;
import com.github.bogdanovmn.translator.web.orm.entity.domain.Word;
import com.github.bogdanovmn.translator.web.orm.repository.common.BaseEntityWithUniqueNameRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRememberedWordRepository extends CrudRepository<UserRememberedWord, Integer> {
	List<UserRememberedWord> getUserRememberedWordsByUser(User user);
}
