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

	public TranslateProvider getProvider() {
		return provider;
	}

	@XmlIDREF
	@XmlAttribute(name = "pid", required = true)
	public Translate setProvider(TranslateProvider provider) {
		this.provider = provider;
		return this;
	}

	public String getValue() {
		return value;
	}

	@XmlAttribute
	public Translate setValue(String value) {
		this.value = value;
		return this;
	}

	public Word getWord() {
		return word;
	}

	@XmlIDREF
	@XmlAttribute(name = "wid", required = true)
	public Translate setWord(Word word) {
		this.word = word;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Translate translate = (Translate) o;

		if (!value.equals(translate.value)) return false;
		if (!provider.equals(translate.provider)) return false;
		return word.equals(translate.word);
	}

	@Override
	public int hashCode() {
		int result = value.hashCode();
		result = 31 * result + provider.hashCode();
		result = 31 * result + word.hashCode();
		return result;
	}
}
