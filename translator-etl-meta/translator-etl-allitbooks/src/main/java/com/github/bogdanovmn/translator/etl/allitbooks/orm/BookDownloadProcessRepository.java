package com.github.bogdanovmn.translator.etl.allitbooks.orm;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookDownloadProcessRepository extends JpaRepository<BookDownloadProcess, Integer> {
	List<BookDownloadProcess> findTop10ByStatus(DownloadStatus status);
}
