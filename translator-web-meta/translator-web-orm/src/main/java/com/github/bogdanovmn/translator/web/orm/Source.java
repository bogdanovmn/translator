package com.github.bogdanovmn.translator.web.orm;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Set;

@Entity
@NamedNativeQuery(
	name = "Source.getAllWithUserStatistic",
	resultSetMapping = "SourceMapping.withUserStatistic",
	query =
		"SELECT s.id, s.raw_name, s.author, s.title, s.words_count, COUNT(urw.word_id) user_words_remembered_count " +
			"FROM source s " +
			"JOIN word2source ws ON ws.source_id = s.id " +
			"LEFT JOIN user_remembered_word urw ON ws.word_id = urw.word_id AND urw.user_id = ?1 " +
			"GROUP BY s.id"
)
@SqlResultSetMapping(
	name = "SourceMapping.withUserStatistic",
	classes = {
		@ConstructorResult(
			targetClass = SourceWithUserStatistic.class,
			columns = {
				@ColumnResult(name = "id", type = Integer.class),
				@ColumnResult(name = "raw_name"),
				@ColumnResult(name = "author"),
				@ColumnResult(name = "title"),
				@ColumnResult(name = "words_count", type = Integer.class),
				@ColumnResult(name = "user_words_remembered_count", type = Integer.class)
			}
		)
	}
)
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

	public Source(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.toString();
	}

	@Override
	public String toString() {
		if (StringUtils.isEmpty(this.author) || StringUtils.isEmpty(this.title)) {
			return this.rawName;
		}
		else {
			return String.format("%s - %s", this.author, this.title);
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
