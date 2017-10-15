package com.github.bogdanovmn.translator.web.orm.repository.app;

import com.github.bogdanovmn.translator.web.orm.entity.app.User;
import com.github.bogdanovmn.translator.web.orm.entity.domain.Word;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
	User findFirstByName(String name);

	User findFirstByEmail(String email);

	List<Word> getWordsToRemember(Integer userId);
}
