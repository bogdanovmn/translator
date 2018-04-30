package com.github.bogdanovmn.translator.web.orm;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;

@Entity
@Table(name = "word2source")
@NamedNativeQueries({
	@NamedNativeQuery(
		name = "WordSource.toRemember",
		resultClass = WordSource.class,
		query = WordSourceNativeQuery.TO_REMEMBER_WORDS_BY_SOURCE
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

	public WordSource() {
	}

	public Integer getCount() {
		return count;
	}

	@XmlAttribute
	public WordSource setCount(Integer count) {
		this.count = count;
		return this;
	}

	public Word getWord() {
		return word;
	}

	@XmlIDREF
	@XmlAttribute(name = "wid")
	public WordSource setWord(Word word) {
		this.word = word;
		return this;
	}

	public Source getSource() {
		return source;
	}

	@XmlIDREF
	@XmlAttribute(name = "sid")
	public WordSource setSource(Source source) {
		this.source = source;
		return this;
	}

	public WordSource incCount(Integer incValue) {
		count += incValue;
		return this;
	}
}
