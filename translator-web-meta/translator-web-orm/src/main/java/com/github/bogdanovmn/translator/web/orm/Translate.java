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
