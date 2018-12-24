package com.github.bogdanovmn.translator.etl.allitbooks.orm;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookDownloadProcessRepository extends JpaRepository<BookDownloadProcess, Integer> {
	List<BookDownloadProcess> findTop10ByStatus(DownloadStatus status);

	@EntityGraph(attributePaths = {"meta"})
	List<BookDownloadProcess> findAllByStatusIsNot(DownloadStatus status);


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
