package com.github.bogdanovmn.translator.web.orm.entity.domain;

import com.github.bogdanovmn.translator.web.orm.entity.common.BaseEntity;

import javax.persistence.Entity;

@Entity
public class WordSource extends BaseEntity {
	private WordSourceType type;
	private String author;
	private String title;
	private String contentHash;

	public WordSourceType getType() {
		return type;
	}

	public WordSource setType(WordSourceType type) {
		this.type = type;
		return this;
	}

	public String getAuthor() {
		return author;
	}

	public WordSource setAuthor(String author) {
		this.author = author;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public WordSource setTitle(String title) {
		this.title = title;
		return this;
	}
}
