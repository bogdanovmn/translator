package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntityRepository;

public interface ProperNameSourceRepository extends BaseEntityRepository<ProperNameSource> {

	void deleteAllBySource(Source source);

}
