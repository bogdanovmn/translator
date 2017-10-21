package com.github.bogdanovmn.translator.web.orm;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.Set;

@Entity
@XmlType
public class Source extends BaseEntity {
//	@Enumerated(EnumType.STRING)
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
	@XmlAttribute
	public SourceType getType() {
		return type;
	}

	public Source setType(SourceType type) {
		this.type = type;
		return this;
	}

	@XmlAttribute
	public String getAuthor() {
		return author;
	}

	public Source setAuthor(String author) {
		this.author = author;
		return this;
	}

	@XmlAttribute
	public String getTitle() {
		return title;
	}

	public Source setTitle(String title) {
		this.title = title;
		return this;
	}

	@XmlAttribute
	public String getContentHash() {
		return contentHash;
	}

	public Source setContentHash(String contentHash) {
		this.contentHash = contentHash;
		return this;
	}

	@XmlTransient
	public Set<WordSource> getWordSources() {
		return wordSources;
	}

	public Source setWordSources(Set<WordSource> wordSources) {
		this.wordSources = wordSources;
		return this;
	}

	@XmlAttribute
	public String getRawName() {
		return rawName;
	}

	public Source setRawName(String rawName) {
		this.rawName = rawName;
		return this;
	}

	@XmlAttribute
	public Integer getWordsCount() {
		return wordsCount;
	}

	public Source setWordsCount(Integer wordsCount) {
		this.wordsCount = wordsCount;
		return this;
	}
}
