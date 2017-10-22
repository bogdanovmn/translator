package com.github.bogdanovmn.translator.web.orm;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;

@Entity
@Table(name = "word2source")
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

	@XmlAttribute
	public Integer getCount() {
		return count;
	}

	public WordSource setCount(Integer count) {
		this.count = count;
		return this;
	}

	@XmlIDREF
	@XmlAttribute(name = "wid")
	public Word getWord() {
		return word;
	}

	public WordSource setWord(Word word) {
		this.word = word;
		return this;
	}

	@XmlIDREF
	@XmlAttribute(name = "sid")
	public Source getSource() {
		return source;
	}

	public WordSource setSource(Source source) {
		this.source = source;
		return this;
	}
}
