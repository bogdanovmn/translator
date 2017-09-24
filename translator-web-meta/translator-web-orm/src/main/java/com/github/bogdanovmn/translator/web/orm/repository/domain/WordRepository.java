package com.github.bogdanovmn.translator.web.orm.repository.domain;

import com.github.bogdanovmn.translator.web.orm.entity.domain.Word;
import org.springframework.data.repository.CrudRepository;

public interface WordRepository extends CrudRepository<Word, Integer> {
}
