package com.github.bogdanovmn.translator.web.orm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface UserHoldOverWordRepository extends JpaRepository<UserHoldOverWord, Integer> {

	UserHoldOverWord findFirstByUserAndWordId(User user, Integer wordId);

	Long removeAllByUser(User user);

	void removeAllByWord(Word word);

	@Modifying
	@Transactional
	void deleteAllByUpdatedBefore(LocalDateTime date);

}
