package com.github.bogdanovmn.translator.web.orm;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRememberedWordRepository extends JpaRepository<UserRememberedWord, Integer> {

	UserRememberedWord findFirstByUserAndWordId(User user, Integer wordId);

	Integer countByUser(User user);

	Long removeAllByUser(User user);

}
