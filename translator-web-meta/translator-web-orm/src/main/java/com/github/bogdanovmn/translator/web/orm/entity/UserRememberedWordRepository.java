package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRememberedWordRepository extends BaseEntityRepository<UserRememberedWord> {

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

	@Query(
		"select count(w) " +
		"from Word w " +
		"join UserRememberedWord uwr on w.id = uwr.word.id and uwr.user.id = :userId " +
		"where w.sourcesCount > 0"
	)
	Integer countByUserWithExistingSourcesOnly(@Param("userId") Integer userId);
}