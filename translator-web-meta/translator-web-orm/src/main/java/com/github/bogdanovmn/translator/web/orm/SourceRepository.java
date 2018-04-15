package com.github.bogdanovmn.translator.web.orm;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SourceRepository extends JpaRepository<Source, Integer> {
	Source findFirstByContentHash(String hash);

	List<SourceWithUserStatistic> getAllWithUserStatistic(Integer userId);
}
