package com.github.bogdanovmn.translator.web.orm.entity.domain;

import com.github.bogdanovmn.translator.web.orm.entity.common.BaseEntityWithUniqueName;

import javax.persistence.Entity;

@Entity
public class TranslateProvider extends BaseEntityWithUniqueName {
	public TranslateProvider(String name) {
		super(name);
	}

	public TranslateProvider() {
	}
}
