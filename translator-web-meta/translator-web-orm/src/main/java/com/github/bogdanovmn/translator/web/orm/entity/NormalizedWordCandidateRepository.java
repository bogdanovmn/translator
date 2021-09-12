package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntityRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NormalizedWordCandidateRepository extends BaseEntityRepository<NormalizedWordCandidate> {
	@Query(
		"select nwc from NormalizedWordCandidate nwc " +
		"order by nwc.base"
	)
	List<NormalizedWordCandidate> findAllSortedByLength();
}
