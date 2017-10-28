package com.github.bogdanovmn.translator.web.orm;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Set;

@Entity
public class Source extends BaseEntity {
	@Enumerated(EnumType.STRING)
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

	@Override
	public String toString() {
		if (this.author != null && this.title != null) {
			return String.format("%s - %s", this.author, this.title);
		}
		else {
			return this.rawName;
		}
	}
	public SourceType getType() {
		return type;
	}

	@XmlAttribute
	public Source setType(SourceType type) {
		this.type = type;
		return this;
	}

	public String getAuthor() {
		return author;
	}

	@XmlAttribute
	public Source setAuthor(String author) {
		this.author = author;
		return this;
	}

	public String getTitle() {
		return title;
	}

	@XmlAttribute
	public Source setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getContentHash() {
		return contentHash;
	}

	@XmlAttribute
	public Source setContentHash(String contentHash) {
		this.contentHash = contentHash;
		return this;
	}

	public Set<WordSource> getWordSources() {
		return wordSources;
	}

	@XmlTransient
	public Source setWordSources(Set<WordSource> wordSources) {
		this.wordSources = wordSources;
		return this;
	}

	public String getRawName() {
		return rawName;
	}

	@XmlAttribute
	public Source setRawName(String rawName) {
		this.rawName = rawName;
		return this;
	}

	public Integer getWordsCount() {
		return wordsCount;
	}

	@XmlAttribute
	public Source setWordsCount(Integer wordsCount) {
		this.wordsCount = wordsCount;
		return this;
	}
}
