package com.github.bogdanovmn.translator.web.orm.entity.domain;

import com.github.bogdanovmn.translator.web.orm.entity.common.BaseEntity;

import javax.persistence.*;

@Entity
public class Translate extends BaseEntity {
	private String value;

	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "translate_source_id")
	private TranslateProvider source;

	public Translate() {
	}

	public TranslateProvider getSource() {
		return source;
	}

	public Translate setSource(TranslateProvider source) {
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
