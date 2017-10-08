package com.github.bogdanovmn.translator.web.orm.repository.app;

import com.github.bogdanovmn.translator.web.orm.entity.app.User;
import com.github.bogdanovmn.translator.web.orm.entity.app.UserHoldOverWord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserHoldOverWordRepository extends CrudRepository<UserHoldOverWord, Integer> {
	List<UserHoldOverWord> findAllByUser(User user);

	UserHoldOverWord findFirstByUserAndWordId(User user, Integer wordId);
}
