package com.github.bogdanovmn.translator.web.orm;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWordProgressRepository extends JpaRepository<UserWordProgress, Integer> {
	UserWordProgress findByUserAndWord(User user, Word word);

	void removeAllByWord(Word word);

}
