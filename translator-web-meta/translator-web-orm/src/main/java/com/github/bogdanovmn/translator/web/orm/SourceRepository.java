package com.github.bogdanovmn.translator.web.orm;

import org.springframework.data.repository.CrudRepository;

public interface SourceRepository extends CrudRepository<Source, Integer> {
	Source findFirstByContentHash(String hash);
}
