package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntityRepository;

public interface UserRepository extends BaseEntityRepository<User> {
	User findFirstByName(String name);

	User findFirstByEmail(String email);

}
