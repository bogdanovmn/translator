package com.github.bogdanovmn.translator.web.orm.entity.domain;

import com.github.bogdanovmn.translator.web.orm.entity.common.BaseEntityWithUniqueName;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Word extends BaseEntityWithUniqueName {
	private int frequence;
	private int sourcesCount;

	@Column(nullable = false)
	private boolean blackList = false;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "word_id")
	private Set<Translate> translates;

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

	public Word setWordSources(Set<WordSource> wordSources) {
		this.wordSources = wordSources;
		return this;
	}

	public Set<Translate> getTranslates() {
		return translates;
	}

	public Word setTranslates(Set<Translate> translates) {
		this.translates = translates;
		return this;
	}

	public boolean isBlackList() {
		return blackList;
	}

	public Word setBlackList(boolean blackList) {
		this.blackList = blackList;
		return this;
	}

	public Integer getFrequence() {
		return frequence;
	}

	public Word setFrequence(Integer frequence) {
		this.frequence = frequence;
		return this;
	}

	public Integer getSourcesCount() {
		return sourcesCount;
	}

	public Word setSourcesCount(Integer sourcesCount) {
		this.sourcesCount = sourcesCount;
		return this;
	}

	public Word incSourcesCount() {
		this.sourcesCount += 1;
		return this;
	}

	public Word incFrequence(int incValue) {
		this.frequence += incValue;
		return this;
	}
}
