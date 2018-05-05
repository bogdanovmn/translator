package com.github.bogdanovmn.translator.web.orm;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHoldOverWordRepository extends JpaRepository<UserHoldOverWord, Integer> {

	UserHoldOverWord findFirstByUserAndWordId(User user, Integer wordId);

	Long removeAllByUser(User user);

	void removeAllByWord(Word word);

}
