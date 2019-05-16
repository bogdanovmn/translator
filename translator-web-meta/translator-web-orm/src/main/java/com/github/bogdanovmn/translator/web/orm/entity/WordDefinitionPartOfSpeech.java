package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntityWithUniqueName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Setter
@Getter
@NoArgsConstructor

@Entity
public class WordDefinitionPartOfSpeech extends BaseEntityWithUniqueName {
	public WordDefinitionPartOfSpeech(String name) {
		super(name);
	}
}
