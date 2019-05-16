package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntityWithUniqueNameRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProperNameRepository extends BaseEntityWithUniqueNameRepository<ProperName> {
	@Query(
		nativeQuery = true,
		value =
			"select pn.name, pns.count " +
			"from proper_name pn " +
			"join proper_name2source pns on pn.id = pns.proper_name_id " +
			"where pns.source_id = :sourceId"
	)
	List<ProperNameForCloud> properNameWordsBySourceId(@Param("sourceId") Integer sourceId);
	interface ProperNameForCloud {
		String getName();
		Integer getCount();
	}

	@Modifying
	@Query(
		nativeQuery = true,
		value =
			"delete proper_name from proper_name " +
			"left join proper_name2source pns on proper_name.id = pns.proper_name_id " +
			"where pns.source_id is null"
	)
	void removeUnused();
}
