package com.github.bogdanovmn.translator.web.orm;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class WordForm extends BaseEntityWithUniqueName {
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "word_id")
	private Word word;
}
