package com.github.bogdanovmn.translator.web.orm;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface WordRepository extends BaseEntityWithUniqueNameRepository<Word> {
	Integer countByBlackListFalse();

	Set<Word> getAllByBlackListFalse();

	List<Word> findAllByBlackListFalseAndFrequenceGreaterThanOrderByName(Integer minimalFrequency);

	List<Word> toRemember(Integer userId);

	List<Word> allKnownWordsByUserForCloud(Integer userId);
	List<Word> allUnknownWordsByUserForCloud(Integer userId);
	List<Word> allUnknownWordsByUserAndSourceForCloud(Integer userId, Integer sourceId);
	List<Word> allKnownWordsByUserAndSourceForCloud(Integer userId, Integer sourceId);

	@Modifying
	@Query(
		nativeQuery = true,
		value =
			"update word " +
			"join ( " +
			"    select w.id, sum(if(ws.id, 1, 0)) sources, sum(ifnull(ws.count, 0)) freq " +
			"    from word w " +
			"    left join word2source ws on w.id = ws.word_id " +
			"    group by w.id " +
			") stat on stat.id = word.id " +
			"set word.frequence = stat.freq, word.sources_count = stat.sources"
	)
	void updateStatistic();
}
