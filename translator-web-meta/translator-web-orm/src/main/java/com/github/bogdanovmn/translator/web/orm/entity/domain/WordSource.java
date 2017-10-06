package com.github.bogdanovmn.translator.web.orm.entity.domain;

import com.github.bogdanovmn.translator.web.orm.entity.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class WordSource extends BaseEntity {
	private WordSourceType type;
	private String author;
	private String title;

	@Column(length = 32, unique = true)
	private String contentHash;

	@ManyToMany(mappedBy = "sources")
	private Set<Word> words;

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

	public String getContentHash() {
		return contentHash;
	}

	public WordSource setContentHash(String contentHash) {
		this.contentHash = contentHash;
		return this;
	}

	public Set<Word> getWords() {
		return words;
	}

	public WordSource setWords(Set<Word> words) {
		this.words = words;
		return this;
	}
}
