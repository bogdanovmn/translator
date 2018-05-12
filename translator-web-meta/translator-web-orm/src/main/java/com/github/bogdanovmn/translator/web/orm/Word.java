package com.github.bogdanovmn.translator.web.orm;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedEntityGraph(
	name = "wordsWithTranslate",
	attributeNodes = @NamedAttributeNode("translates")
)
@NamedNativeQueries({
	@NamedNativeQuery(
		name = "Word.toRemember",
		resultClass = Word.class,
		query = WordNativeQuery.TO_REMEMBER_WORDS
	)
})
public class Word extends BaseEntityWithUniqueName {
	private int frequence;
	private int sourcesCount;

	@Column(nullable = false)
	private Boolean blackList = false;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "word_id")
	private Set<Translate> translates = new HashSet<>();

	@OneToMany(mappedBy = "word")
	private Set<WordSource> wordSources;

	public Word(String name) {
		super(name);
	}

	public Word(Integer id) {
		super(id);
	}

	public Word() {
	}

	public Set<WordSource> getWordSources() {
		return wordSources;
	}

	@XmlTransient
	public Word setWordSources(Set<WordSource> wordSources) {
		this.wordSources = wordSources;
		return this;
	}

	public Set<Translate> getTranslates() {
		return translates;
	}

	@XmlTransient
	public Word setTranslates(Set<Translate> translates) {
		this.translates = translates;
		return this;
	}

	public Boolean isBlackList() {
		return blackList;
	}

	@XmlAttribute
	@XmlJavaTypeAdapter(ExportToXmlBooleanAdapter.class)
	public Word setBlackList(Boolean blackList) {
		this.blackList = blackList;
		return this;
	}

	public Integer getFrequence() {
		return frequence;
	}

	@XmlTransient
	public Word setFrequence(Integer frequence) {
		this.frequence = frequence;
		return this;
	}

	public Integer getSourcesCount() {
		return sourcesCount;
	}

	@XmlTransient
	public Word setSourcesCount(Integer sourcesCount) {
		this.sourcesCount = sourcesCount;
		return this;
	}

	public Word incFrequence(int incValue) {
		this.frequence += incValue;
		return this;
	}

	public void addWordSources(Collection<WordSource> sources) {
		wordSources.addAll(sources);
		sourcesCount += sources.size();
	}
}
