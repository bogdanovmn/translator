package com.github.bogdanovmn.translator.web.orm.repository.domain;

import com.github.bogdanovmn.translator.web.orm.entity.domain.Word;
import com.github.bogdanovmn.translator.web.orm.entity.domain.WordSource;
import org.springframework.data.repository.CrudRepository;

public interface WordSourceRepository extends CrudRepository<WordSource, Integer> {
	Integer countByWord(Word word);
}
