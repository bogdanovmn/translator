package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntityWithUniqueNameRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface WordRepository extends BaseEntityWithUniqueNameRepository<Word> {
	Integer countByBlackListFalseAndSourcesCountGreaterThan(Integer sourcesCount);

	Set<Word> getAllByBlackListFalse();

	List<Word> findAllByBlackListFalseAndFrequencyGreaterThanOrderByName(Integer minimalFrequency);


	List<Word> allRememberedForCloud(Integer userId);
	List<Word> allUnknownForCloud(Integer userId);
	List<Word> allUnknownBySourceForCloud(Integer userId, Integer sourceId);
	List<Word> allRememberedBySourceForCloud(Integer userId, Integer sourceId);
	List<Word> allBySourceForCloud(Integer sourceId);

	@Query(
		"select w from Word w " +
		"join UserRememberedWord urw on w.id = urw.word.id " +
		"group by w "
	)
	List<Word> allRememberedForExport();

	@Query(
		"select w as word, uwp as userProgress"
			+ " from Word w"
			+ " left join UserHoldOverWord uhow on w.id = uhow.word.id and uhow.user.id = :userId "
			+ " left join UserRememberedWord urw on w.id = urw.word.id and urw.user.id = :userId "
			+ " left join UserWordProgress uwp on w.id = uwp.word.id and uwp.user.id = :userId "
			+ " where urw.word.id is null "
			+ " and   uhow.word.id is null "
			+ " and   w.sourcesCount > 0 "
			+ " and   w.blackList = 0 "
//			+ " order by w.sourcesCount desc, w.frequency desc"
	)
	List<WordWithUserProgress> unknownByAllSources(
		@Param("userId") Integer userId,
		Pageable pageable
	);
	interface WordWithUserProgress {
		Word getWord();
		UserWordProgress getUserProgress();
	}

	@Query(
		"select w"
		+ " from Word w"
		+ " left join UserHoldOverWord uhow on w.id = uhow.word.id and uhow.user.id = :userId "
		+ " left join UserRememberedWord urw on w.id = urw.word.id and urw.user.id = :userId "
		+ " where urw.word.id is null "
		+ " and   uhow.word.id is null "
		+ " and   w.sourcesCount > 0 "
		+ " and   w.blackList = 0 "
//		+ " order by w.name"
	)
	Page<Word> unknownByAllSourcesLite(
		@Param("userId") Integer userId,
		Pageable pageable
	);

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
//			+ " order by ws.count desc"
	)
	List<WordBySourceWithUserProgress> unknownBySource(
		@Param("userId") Integer userId,
		@Param("sourceId") Integer sourceId,
		Pageable pageable
	);
	interface WordBySourceWithUserProgress extends WordWithUserProgress {
		WordSource getWordSource();
		default Word getWord() {
			return getWordSource().getWord();
		}
		default int getFrequency() {
			return getWordSource().getCount();
		}
	}

	@Query(
		"select w"
		+ " from WordSource ws"
		+ " join Word w on w.id = ws.word.id"
		+ " left join UserHoldOverWord uhow  on w.id = uhow.word.id and uhow.user.id = :userId "
		+ " left join UserRememberedWord urw on w.id = urw.word.id  and urw.user.id = :userId "
		+ " where urw.word.id is NULL "
		+ " and   uhow.word.id is NULL "
		+ " and   w.blackList = 0 "
		+ " and   ws.source.id = :sourceId"
//		+ " order by w.name"
	)
	Page<Word> unknownBySourceLite(
		@Param("userId") Integer userId,
		@Param("sourceId") Integer sourceId,
		Pageable pageable
	);

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
			"set word.frequency = stat.freq, word.sources_count = stat.sources"
	)
	void updateStatistic();

	@Modifying
	@Query(
		nativeQuery = true,
		value =
			"delete word from word " +
			"left join user_remembered_word urw on word.id = urw.word_id " +
			"left join user_hold_over_word uhow on word.id = uhow.word_id " +
			"left join user_word_progress uwp   on word.id = uwp.word_id " +
			"left join word_definition_service_log wdl on word.id = wdl.word_id " +
			"where word.black_list = 0 " +
			"and   word.sources_count = 0 " +
			"and   urw.id  is null " +
			"and   uhow.id is null " +
			"and   wdl.id   is null " +
			"and   uwp.id  is null"
	)
	void removeUnused();
}
