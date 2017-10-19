package com.github.bogdanovmn.translator.web.orm;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRememberedWordRepository extends CrudRepository<UserRememberedWord, Integer> {
	List<UserRememberedWord> findAllByUser(User user);

	UserRememberedWord findFirstByUserAndWordId(User user, Integer wordId);

	Integer countByUser(User user);
}
