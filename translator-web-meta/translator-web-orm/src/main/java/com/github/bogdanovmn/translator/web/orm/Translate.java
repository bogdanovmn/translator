package com.github.bogdanovmn.translator.web.orm;

import com.github.bogdanovmn.translator.orm.core.BaseEntity;
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
public class Translate extends BaseEntity {
	@XmlAttribute
	private String value;

	@XmlIDREF
	@XmlAttribute(name = "pid", required = true)
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "provider_id")
	private TranslateProvider provider;

	@XmlIDREF
	@XmlAttribute(name = "wid", required = true)
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "word_id")
	private Word word;

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
