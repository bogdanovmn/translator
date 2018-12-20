package com.github.bogdanovmn.translator.web.orm;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface WordSourceRepository extends JpaRepository<WordSource, Integer> {
	List<WordSource> toRemember(Integer userId, Integer sourceId, Integer popularCount, Integer rareCount);
	Set<WordSource> findAllByWord(Word word);

	@EntityGraph(attributePaths = {"word"})
	Set<WordSource> findAllBySourceId(Integer sourceId);

	void deleteAllBySource(Source source);
}
