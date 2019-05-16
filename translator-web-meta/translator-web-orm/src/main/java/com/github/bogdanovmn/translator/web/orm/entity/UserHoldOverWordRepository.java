package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntityRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface UserHoldOverWordRepository extends BaseEntityRepository<UserHoldOverWord> {

	UserHoldOverWord findFirstByUserAndWordId(User user, Integer wordId);

	Long removeAllByUser(User user);

	void removeAllByWord(Word word);

	@Modifying
	@Transactional
	void deleteAllByUpdatedBefore(LocalDateTime date);

	@Query(
		"select uwho from UserHoldOverWord uwho" +
			" left join WordDefinition wd on wd.word.id = uwho.word.id" +
			" left join WordDefinitionServiceLog sl on sl.word.id = uwho.word.id" +
			" where wd is null and sl is null"
	)
	@EntityGraph(attributePaths = "word")
	List<UserHoldOverWord> allWithoutDefinition(Pageable pageable);

}
