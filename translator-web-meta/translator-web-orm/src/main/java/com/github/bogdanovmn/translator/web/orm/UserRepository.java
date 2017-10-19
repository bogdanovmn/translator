package com.github.bogdanovmn.translator.web.orm;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
	User findFirstByName(String name);

	User findFirstByEmail(String email);

	List<Word> getWordsToRemember(Integer userId);
}
