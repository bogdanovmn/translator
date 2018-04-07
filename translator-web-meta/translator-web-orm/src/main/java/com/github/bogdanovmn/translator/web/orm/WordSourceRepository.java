package com.github.bogdanovmn.translator.web.orm;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordSourceRepository extends JpaRepository<WordSource, Integer> {
	List<WordSource> toRemember(Integer userId, Integer sourceId);
}
