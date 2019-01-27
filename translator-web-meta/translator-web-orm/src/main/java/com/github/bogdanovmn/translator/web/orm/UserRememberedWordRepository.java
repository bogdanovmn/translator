package com.github.bogdanovmn.translator.web.orm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRememberedWordRepository extends JpaRepository<UserRememberedWord, Integer> {

	UserRememberedWord findFirstByUserAndWordId(User user, Integer wordId);

	Integer countByUser(User user);

	Long removeAllByUser(User user);

	void deleteAllByWord(Word word);

	@Query(
		nativeQuery = true,
		value =
			"SELECT COUNT(1) cnt FROM user_remembered_word urw " +
				"JOIN word2source ws ON ws.word_id = urw.word_id AND urw.user_id = :userId AND ws.source_id = :sourceId"
	)
	Long getCountBySource(@Param("userId") Integer userId, @Param("sourceId") Integer sourceId);
}