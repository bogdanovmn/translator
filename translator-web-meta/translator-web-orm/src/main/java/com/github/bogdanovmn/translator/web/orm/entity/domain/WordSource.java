package com.github.bogdanovmn.translator.web.orm.entity.domain;

import com.github.bogdanovmn.translator.web.orm.entity.common.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "word2source")
public class WordSource extends BaseEntity {
	private Integer count;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "word_id")
	private Word word;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "source_id")
	private Source source;

	public WordSource() {
	}

	public Integer getCount() {
		return count;
	}

	public WordSource setCount(Integer count) {
		this.count = count;
		return this;
	}

	public Word getWord() {
		return word;
	}

	public WordSource setWord(Word word) {
		this.word = word;
		return this;
	}

	public Source getSource() {
		return source;
	}

	public WordSource setSource(Source source) {
		this.source = source;
		return this;
	}
}
