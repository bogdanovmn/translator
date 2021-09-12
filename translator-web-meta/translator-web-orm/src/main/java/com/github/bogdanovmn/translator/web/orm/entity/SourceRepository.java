package com.github.bogdanovmn.translator.web.orm.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SourceRepository extends JpaRepository<Source, Integer> {
	Source findFirstByContentHash(String hash);

	@Query(
		"SELECT s AS source, COUNT(urw.id) AS userWordsRememberedCount " +
		"FROM Source s " +
		"JOIN WordSource ws ON ws.source.id = s.id " +
		"JOIN Word w ON w.id = ws.word.id and w.blackList = 0 " +
		"LEFT JOIN UserRememberedWord urw ON urw.word.id = ws.word.id AND urw.user.id = ?1 " +
		"GROUP BY s " +
		"ORDER BY s.id DESC"
	)
	List<WithUserStatistic> getAllWithUserStatistic(Integer userId);

	interface WithUserStatistic {
		Source getSource();
		Integer getUserWordsRememberedCount();
		default Integer getUserWordsRememberedPercent() {
			return (int)(100.0 * getUserWordsRememberedCount()) / (getSource().getWordsCount() - getSource().getBlackListCount());
		}
	}

	@Modifying
	@Query(
		nativeQuery = true,
		value =
			"update source s " +
			"join (" +
				"select w2s.source_id, count(w.id) cnt " +
				"from word2source w2s " +
				"join word w on w2s.word_id = w.id " +
				"where w.black_list = 1 " +
				"group by w2s.source_id" +
			") stat on stat.source_id = s.id " +
			"set s.black_list_count = stat.cnt "
	)
	void updateBlackListValue();
}
