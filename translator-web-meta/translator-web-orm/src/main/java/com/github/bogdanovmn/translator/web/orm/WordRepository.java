package com.github.bogdanovmn.translator.web.orm;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface WordRepository extends BaseEntityWithUniqueNameRepository<Word> {
	Integer countByBlackListFalse();
	Set<Word> getAllByBlackListFalse();

	List<Word> toRemember(Integer userId);

	@Modifying
	@Query(
		nativeQuery = true,
		value =
			"update word " +
			"join ( " +
			"    select w.id, sum(1) sources, sum(ws.count) freq " +
			"    from word w " +
			"    join word2source ws on w.id = ws.word_id " +
			"    group by w.id " +
			") stat on stat.id = word.id " +
			"set word.frequence = stat.freq, word.sources_count = stat.sources"
	)
	void updateStatistic();
}
