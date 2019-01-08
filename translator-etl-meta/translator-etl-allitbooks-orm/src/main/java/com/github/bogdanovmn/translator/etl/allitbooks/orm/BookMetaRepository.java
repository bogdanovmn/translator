package com.github.bogdanovmn.translator.etl.allitbooks.orm;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookMetaRepository extends JpaRepository<BookMeta, Integer> {

	BookMeta findBookByOriginalUrl(String url);

	List<BookMeta> findAllByOrderByTitle();

	@Query(
		"select meta from BookMeta meta " +
			"left join BookDownloadProcess process on process.meta.id = meta.id " +
			"where process is null " +
			"and meta.obsolete = false " +
			"and (meta.fileSizeMb < 70 or meta.fileSizeMb <> 0)"
	)
	List<BookMeta> notProcessed(Pageable pageable);
}
