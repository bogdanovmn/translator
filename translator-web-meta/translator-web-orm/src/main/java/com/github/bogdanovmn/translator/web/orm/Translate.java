package com.github.bogdanovmn.translator.web.orm;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;

@Entity
public class Translate extends BaseEntity {
	private String value;

	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "provider_id")
	private TranslateProvider provider;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "word_id")
	private Word word;

	public Translate() {
	}

	@XmlIDREF
	@XmlAttribute(name = "pid")
	public TranslateProvider getProvider() {
		return provider;
	}

	public Translate setProvider(TranslateProvider provider) {
		this.provider = provider;
		return this;
	}

	@XmlAttribute
	public String getValue() {
		return value;
	}

	public Translate setValue(String value) {
		this.value = value;
		return this;
	}

	@XmlIDREF
	@XmlAttribute(name = "wid")
	public Word getWord() {
		return word;
	}

	public Translate setWord(Word word) {
		this.word = word;
		return this;
	}
}
