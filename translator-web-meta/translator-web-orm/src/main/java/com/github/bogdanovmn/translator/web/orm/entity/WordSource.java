package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "word2source")
@NamedNativeQueries({
	@NamedNativeQuery(
		name = "WordSource.toRemember",
		resultClass = WordSource.class,
		query = WordSourceNativeQuery.UNKNOWN_WORDS_BY_SOURCE
	)
})
public class WordSource extends BaseEntity {
	@XmlAttribute
	private Integer count;

	@XmlIDREF
	@XmlAttribute(name = "wid")
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "word_id")
	private Word word;

	@XmlIDREF
	@XmlAttribute(name = "sid")
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "source_id")
	private Source source;

	public WordSource incCount(Integer incValue) {
		count += incValue;
		return this;
	}
}
