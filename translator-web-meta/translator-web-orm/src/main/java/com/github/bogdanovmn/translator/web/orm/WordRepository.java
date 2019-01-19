package com.github.bogdanovmn.translator.web.orm;

import com.github.bogdanovmn.translator.orm.core.BaseEntityWithUniqueNameRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface WordRepository extends BaseEntityWithUniqueNameRepository<Word> {
	Integer countByBlackListFalse();

	Set<Word> getAllByBlackListFalse();

	List<Word> findAllByBlackListFalseAndFrequenceGreaterThanOrderByName(Integer minimalFrequency);


	List<Word> allRememberedForCloud(Integer userId);
	List<Word> allUnknownForCloud(Integer userId);
	List<Word> allUnknownBySourceForCloud(Integer userId, Integer sourceId);
	List<Word> allRememberedBySourceForCloud(Integer userId, Integer sourceId);
	List<Word> allBySourceForCloud(Integer sourceId);

	@Query(
		"select w as word, uwp as userProgress"
			+ " from Word w"
			+ " left join UserHoldOverWord uhow on w.id = uhow.word.id and uhow.user.id = :userId "
			+ " left join UserRememberedWord urw on w.id = urw.word.id and urw.user.id = :userId "
			+ " left join UserWordProgress uwp on w.id = uwp.word.id and uwp.user.id = :userId "
			+ " where urw.word.id is null "
			+ " and   uhow.word.id is null "
			+ " and   w.blackList = 0 "
			+ " order by w.sourcesCount desc, w.frequence desc"
	)
//	@EntityGraph(attributePaths = "definitions")
	List<WordWithUserProgress> unknownByAllSources(
		@Param("userId") Integer userId,
		Pageable pageable
	);
	interface WordWithUserProgress {
		Word getWord();
		UserWordProgress getUserProgress();
	}

	@Query(
		"select ws as wordSource, uwp as userProgress"
			+ " from WordSource ws"
			+ " join Word w on w.id = ws.word.id"
			+ " left join UserHoldOverWord uhow  on w.id = uhow.word.id and uhow.user.id = :userId "
			+ " left join UserRememberedWord urw on w.id = urw.word.id  and urw.user.id = :userId "
			+ " left join UserWordProgress uwp   on w.id = uwp.word.id  and uwp.user.id = :userId "
			+ " where urw.word.id is NULL "
			+ " and   uhow.word.id is NULL "
			+ " and   w.blackList = 0 "
			+ " and   ws.source.id = :sourceId"
			+ " order by ws.count desc"
	)
	List<WordBySourceWithUserProgress> unknownBySource(
		@Param("userId") Integer userId,
		@Param("sourceId") Integer sourceId,
		Pageable pageable
	);
	interface WordBySourceWithUserProgress {
		WordSource getWordSource();
		UserWordProgress getUserProgress();
		default Word getWord() {
			return getWordSource().getWord();
		}
		default int getFrequency() {
			return getWordSource().getCount();
		}
	}

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
