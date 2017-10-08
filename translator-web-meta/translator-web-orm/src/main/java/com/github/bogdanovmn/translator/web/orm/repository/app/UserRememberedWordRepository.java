package com.github.bogdanovmn.translator.web.orm.repository.app;

import com.github.bogdanovmn.translator.web.orm.entity.app.User;
import com.github.bogdanovmn.translator.web.orm.entity.app.UserRememberedWord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRememberedWordRepository extends CrudRepository<UserRememberedWord, Integer> {
	List<UserRememberedWord> findAllByUser(User user);

	UserRememberedWord findFirstByUserAndWordId(User user, Integer wordId);
}
