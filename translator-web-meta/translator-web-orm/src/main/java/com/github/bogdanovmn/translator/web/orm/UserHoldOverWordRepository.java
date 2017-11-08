package com.github.bogdanovmn.translator.web.orm;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserHoldOverWordRepository extends CrudRepository<UserHoldOverWord, Integer> {
	List<UserHoldOverWord> findAllByUser(User user);

	UserHoldOverWord findFirstByUserAndWordId(User user, Integer wordId);

	Long removeAllByUser(User user);
}
