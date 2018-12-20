package com.github.bogdanovmn.translator.web.orm;

import com.github.bogdanovmn.translator.orm.core.BaseEntityWithUniqueNameRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface WordRepository extends BaseEntityWithUniqueNameRepository<Word> {
	Integer countByBlackListFalse();

	Set<Word> getAllByBlackListFalse();

	List<Word> findAllByBlackListFalseAndFrequenceGreaterThanOrderByName(Integer minimalFrequency);

	List<Word> toRemember(Integer userId, Integer popularCount, Integer rareCount);

	List<Word> allRememberedForCloud(Integer userId);
	List<Word> allUnknownForCloud(Integer userId);
	List<Word> allUnknownBySourceForCloud(Integer userId, Integer sourceId);
	List<Word> allRememberedBySourceForCloud(Integer userId, Integer sourceId);
	List<Word> allBySourceForCloud(Integer sourceId);

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
