package com.github.bogdanovmn.translator.web.orm;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRememberedWordRepository extends JpaRepository<UserRememberedWord, Integer> {

	UserRememberedWord findFirstByUserAndWordId(User user, Integer wordId);

	Integer countByUser(User user);

	Long removeAllByUser(User user);

	List<Word> getAll(Integer userId);
	List<Word> getAllBySource(Integer userId, Integer sourceId);
}