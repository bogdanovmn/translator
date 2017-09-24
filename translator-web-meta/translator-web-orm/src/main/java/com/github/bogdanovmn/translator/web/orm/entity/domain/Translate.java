package com.github.bogdanovmn.translator.web.orm.entity.domain;

import com.github.bogdanovmn.translator.web.orm.entity.common.BaseEntity;

import javax.persistence.Entity;

@Entity
public class Translate extends BaseEntity {
	private TranslateSource source;
	private String value;

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
