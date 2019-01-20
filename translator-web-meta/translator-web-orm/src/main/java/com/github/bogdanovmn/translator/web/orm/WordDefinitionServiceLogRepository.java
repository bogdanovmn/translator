package com.github.bogdanovmn.translator.web.orm;

import com.github.bogdanovmn.translator.orm.core.BaseEntityRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WordDefinitionServiceLogRepository extends BaseEntityRepository<WordDefinitionServiceLog> {

	@EntityGraph(attributePaths = "word")
	List<WordDefinitionServiceLog> findAllByOrderByUpdatedDesc(Pageable pageable);

	@EntityGraph(attributePaths = "word")
	List<WordDefinitionServiceLog> findAllByStatusOrderByUpdatedDesc(WordDefinitionServiceLog.Status status, Pageable pageable);

	@Query(
		"SELECT l.status AS status, COUNT(l.id) AS count " +
			"FROM WordDefinitionServiceLog l " +
			"GROUP BY l.status"
	)
	List<StatusStatistic> statusStatistic();

	interface StatusStatistic {
		WordDefinitionServiceLog.Status getStatus();
		int getCount();
	}
}
