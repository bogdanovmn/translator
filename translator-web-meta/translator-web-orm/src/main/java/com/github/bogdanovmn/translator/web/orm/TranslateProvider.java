package com.github.bogdanovmn.translator.web.orm;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlType;

@Entity
@XmlType
public class TranslateProvider extends BaseEntityWithUniqueName {
	public TranslateProvider(String name) {
		super(name);
	}

	public TranslateProvider() {
	}
}
