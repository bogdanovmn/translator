package com.github.bogdanovmn.translator.web.orm.entity.domain;

import com.github.bogdanovmn.translator.web.orm.entity.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Source extends BaseEntity {
	private SourceType type;
	private String author;
	private String title;
	private String rawName;
	private Integer wordsCount;

	@Column(length = 32, unique = true)
	private String contentHash;

	@OneToMany(mappedBy = "source")
	private Set<WordSource> wordSources;

	public Source() {
	}

	public SourceType getType() {
		return type;
	}

	public Source setType(SourceType type) {
		this.type = type;
		return this;
	}

	public String getAuthor() {
		return author;
	}

	public Source setAuthor(String author) {
		this.author = author;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Source setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getContentHash() {
		return contentHash;
	}

	public Source setContentHash(String contentHash) {
		this.contentHash = contentHash;
		return this;
	}

	public Set<WordSource> getWordSources() {
		return wordSources;
	}

	public Source setWordSources(Set<WordSource> wordSources) {
		this.wordSources = wordSources;
		return this;
	}

	public String getRawName() {
		return rawName;
	}

	public Source setRawName(String rawName) {
		this.rawName = rawName;
		return this;
	}

	public Integer getWordsCount() {
		return wordsCount;
	}

	public Source setWordsCount(Integer wordsCount) {
		this.wordsCount = wordsCount;
		return this;
	}
}
