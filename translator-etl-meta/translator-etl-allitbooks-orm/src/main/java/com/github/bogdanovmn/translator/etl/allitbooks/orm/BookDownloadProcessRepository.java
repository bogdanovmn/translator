package com.github.bogdanovmn.translator.etl.allitbooks.orm;

import com.github.bogdanovmn.common.spring.jpa.BaseEntityRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookDownloadProcessRepository extends BaseEntityRepository<BookDownloadProcess> {
	List<BookDownloadProcess> findTop10ByStatus(DownloadStatus status);

	@EntityGraph(attributePaths = {"meta"})
	List<BookDownloadProcess> findAllByStatusIsNotInOrderByUpdatedDesc(List<DownloadStatus> statuses);

	@EntityGraph(attributePaths = {"meta"})
	List<BookDownloadProcess> findAllByStatusOrderByUpdatedDesc(DownloadStatus status);

	@Query(
		"SELECT p.status AS status, COUNT(p.id) AS count " +
		"FROM BookDownloadProcess p " +
		"GROUP BY p.status"
	)
	List<DownloadStatusStatistic> statusStatistic();

	interface DownloadStatusStatistic {
		DownloadStatus getStatus();
		int getCount();
	}
}
