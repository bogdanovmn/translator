package com.github.bogdanovmn.translator.web.orm;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findFirstByName(String name);

	User findFirstByEmail(String email);

}
