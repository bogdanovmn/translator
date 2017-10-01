package com.github.bogdanovmn.translator.web.orm.repository.app;

import com.github.bogdanovmn.translator.web.orm.entity.app.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
	User findFirstByName(String name);
	User findFirstByEmail(String email);
}
