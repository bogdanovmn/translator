package com.github.bogdanovmn.translator.web.orm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SourceRepository extends JpaRepository<Source, Integer> {
	Source findFirstByContentHash(String hash);

	@Query(
		"SELECT new com.github.bogdanovmn.translator.web.orm.SourceWithUserStatistic(s, COUNT(s)) "
		+ "FROM Source s "
		+ "JOIN s.wordSources ws, "
		+ "UserRememberedWord urw "
		+ "WHERE urw.user.id = ?1 "
			+ " AND urw.word.id = ws.word.id "
		+ "GROUP BY s.id"
	)
	List<SourceWithUserStatistic> getAllWithUserStatistic(Integer userId);
}
