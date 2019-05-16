package com.github.bogdanovmn.translator.web.orm.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SourceRepository extends JpaRepository<Source, Integer> {
	Source findFirstByContentHash(String hash);

	@Query(
		"SELECT s AS source, COUNT(urw.id) AS userWordsRememberedCount " +
		"FROM Source s " +
		"JOIN WordSource ws ON ws.source.id = s.id " +
		"LEFT JOIN UserRememberedWord urw ON urw.word.id = ws.word.id AND urw.user.id = ?1 " +
		"GROUP BY s " +
		"ORDER BY s.id DESC"
	)
	List<WithUserStatistic> getAllWithUserStatistic(Integer userId);

	interface WithUserStatistic {
		Source getSource();
		Integer getUserWordsRememberedCount();
		default Integer getUserWordsRememberedPercent() {
			return (int)(100.0 * getUserWordsRememberedCount()) / getSource().getWordsCount();
		}
	}
}
