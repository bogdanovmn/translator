package com.github.bogdanovmn.translator.web.orm.repository.domain;

import com.github.bogdanovmn.translator.web.orm.entity.domain.WordSource;
import org.springframework.data.repository.CrudRepository;

public interface WordSourceRepository extends CrudRepository<WordSource, Integer> {
}
