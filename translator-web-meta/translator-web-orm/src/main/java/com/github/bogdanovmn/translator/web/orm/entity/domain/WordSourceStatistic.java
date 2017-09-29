package com.github.bogdanovmn.translator.web.orm.entity.domain;

import com.github.bogdanovmn.translator.web.orm.entity.common.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class WordSourceStatistic extends BaseEntity {
	private Integer count = 0;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "word_id")
	private Word word;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "word_source_id")
	private WordSource source;
}
