package com.github.bogdanovmn.translator.web.orm.repository.domain;

import com.github.bogdanovmn.translator.web.orm.entity.domain.Source;
import org.springframework.data.repository.CrudRepository;

public interface SourceRepository extends CrudRepository<Source, Integer> {
	Source findFirstByContentHash(String hash);
}
