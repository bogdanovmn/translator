package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(
	name = "word2source",
	uniqueConstraints = {
		@UniqueConstraint(
			columnNames = {"word_id", "source_id"}
		)
	}
)
@NamedNativeQueries({
	@NamedNativeQuery(
		name = "WordSource.toRemember",
		resultClass = WordSource.class,
		query = WordSourceNativeQuery.UNKNOWN_WORDS_BY_SOURCE
	)
})
public class WordSource extends BaseEntity {
	private Integer count;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "word_id")
	private Word word;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "source_id")
	private Source source;

	public WordSource incCount(Integer incValue) {
		count += incValue;
		return this;
	}
}
