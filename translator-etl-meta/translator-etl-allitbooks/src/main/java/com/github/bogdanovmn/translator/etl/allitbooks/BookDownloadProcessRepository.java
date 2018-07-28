package com.github.bogdanovmn.translator.etl.allitbooks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookDownloadProcessRepository extends JpaRepository<BookDownloadProcess, Integer> {
	List<BookDownloadProcess> findTop2ByStatus(DownloadStatus status);
}
