package com.github.bogdanovmn.translator.web.orm;

import org.springframework.data.repository.CrudRepository;

public interface WordSourceRepository extends CrudRepository<WordSource, Integer> {
	Integer countByWord(Word word);
}
