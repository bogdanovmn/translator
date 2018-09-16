package com.github.bogdanovmn.translator.web.orm;

import com.github.bogdanovmn.translator.orm.core.BaseEntityWithUniqueName;

import javax.persistence.Entity;

@Entity
public class TranslateProvider extends BaseEntityWithUniqueName {
	public TranslateProvider(String name) {
		super(name);
	}

	public TranslateProvider() {
	}
}
