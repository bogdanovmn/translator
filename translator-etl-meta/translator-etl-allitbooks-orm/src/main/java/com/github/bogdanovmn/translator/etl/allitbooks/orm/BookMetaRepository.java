package com.github.bogdanovmn.translator.etl.allitbooks.orm;

import com.github.bogdanovmn.common.spring.jpa.BaseEntityRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookMetaRepository extends BaseEntityRepository<BookMeta> {

	BookMeta findBookByOriginalUrl(String url);

	List<BookMeta> findAllByOrderByTitle();

	@Query(
		"select meta from BookMeta meta " +
			"left join BookDownloadProcess process on process.meta.id = meta.id " +
			"where process is null " +
			"and meta.obsolete = false " +
			"and (meta.fileSizeMb < 70 and meta.fileSizeMb <> 0)"
	)
	List<BookMeta> notProcessed(Pageable pageable);
}
