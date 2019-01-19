package com.github.bogdanovmn.translator.web.orm;

import com.github.bogdanovmn.translator.orm.core.BaseEntityWithUniqueName;
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
