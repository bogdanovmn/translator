package com.github.bogdanovmn.translator.web.orm.entity.domain;

import com.github.bogdanovmn.translator.web.orm.entity.common.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Translate extends BaseEntity {
	private String value;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "translate_source_id")
	private TranslateSource source;

	public TranslateSource getSource() {
		return source;
	}

	public Translate setSource(TranslateSource source) {
		this.source = source;
		return this;
	}

	public String getValue() {
		return value;
	}

	public Translate setValue(String value) {
		this.value = value;
		return this;
	}
}
