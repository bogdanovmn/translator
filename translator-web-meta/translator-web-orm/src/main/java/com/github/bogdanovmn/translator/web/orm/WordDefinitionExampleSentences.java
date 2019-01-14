package com.github.bogdanovmn.translator.web.orm;

import com.github.bogdanovmn.translator.orm.core.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Setter
@Getter
@NoArgsConstructor

@Entity
public class WordDefinitionExampleSentences extends BaseEntity {
	private String value;

	private int placeHolderPosition;
}
