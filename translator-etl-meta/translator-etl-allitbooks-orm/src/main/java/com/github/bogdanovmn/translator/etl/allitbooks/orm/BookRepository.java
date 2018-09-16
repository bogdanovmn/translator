package com.github.bogdanovmn.translator.etl.allitbooks.orm;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
