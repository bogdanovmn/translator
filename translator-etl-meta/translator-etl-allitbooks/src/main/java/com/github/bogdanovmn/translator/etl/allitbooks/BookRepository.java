package com.github.bogdanovmn.translator.etl.allitbooks;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
	Book findBookByOriginalUrl(String url);
}
