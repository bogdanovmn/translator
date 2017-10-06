package com.github.bogdanovmn.translator.web.orm.entity.domain;

import com.github.bogdanovmn.translator.web.orm.entity.common.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class WordSourceStatistic extends BaseEntity {
	private Integer count = 0;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "word_id")
	private Word word;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "word_source_id")
	private WordSource source;

	public Integer getCount() {
		return count;
	}

	public WordSourceStatistic setCount(Integer count) {
		this.count = count;
		return this;
	}

	public Word getWord() {
		return word;
	}

	public WordSourceStatistic setWord(Word word) {
		this.word = word;
		return this;
	}

	public WordSource getSource() {
		return source;
	}

	public WordSourceStatistic setSource(WordSource source) {
		this.source = source;
		return this;
	}
}
