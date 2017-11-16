package com.github.bogdanovmn.translator.etl.allitbooks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookMetaRepository extends JpaRepository<BookMeta, Integer> {
	BookMeta findBookByOriginalUrl(String url);
	List<BookMeta> findAllByOrderByTitle();
}
