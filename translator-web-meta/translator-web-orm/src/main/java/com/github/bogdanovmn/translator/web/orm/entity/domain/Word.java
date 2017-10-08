package com.github.bogdanovmn.translator.web.orm.entity.domain;

import com.github.bogdanovmn.translator.web.orm.entity.common.BaseEntityWithUniqueName;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Word extends BaseEntityWithUniqueName {
	@Column(nullable = false)
	private boolean blackList = false;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "word_id")
	private Set<Translate> translates;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name = "word2word_source",
		joinColumns = @JoinColumn(name = "word_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "word_source_id", referencedColumnName = "id")
	)
	private Set<WordSource> sources;


	public Set<Translate> getTranslates() {
		return translates;
	}

	public Word setTranslates(Set<Translate> translates) {
		this.translates = translates;
		return this;
	}

	public Set<WordSource> getSources() {
		return sources;
	}

	public Word setSources(Set<WordSource> sources) {
		this.sources = sources;
		return this;
	}

	public Word addSource(WordSource source) {
		if (this.sources == null) {
			this.sources = new HashSet<>();
		}
		this.sources.add(source);
		return this;
	}

	public boolean isBlackList() {
		return blackList;
	}

	public Word setBlackList(boolean blackList) {
		this.blackList = blackList;
		return this;
	}
}
