package com.github.bogdanovmn.translator.web.orm;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findFirstByName(String name);

	User findFirstByEmail(String email);

	List<Word> getWordsToRemember(Integer userId);
}
