package com.github.bogdanovmn.translator.etl.allitbooks.orm;

import com.github.bogdanovmn.translator.etl.allitbooks.orm.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
