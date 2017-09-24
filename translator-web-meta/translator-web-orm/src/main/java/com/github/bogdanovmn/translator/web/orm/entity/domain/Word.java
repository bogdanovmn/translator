package com.github.bogdanovmn.translator.web.orm.entity.domain;

import com.github.bogdanovmn.translator.web.orm.entity.common.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Word extends BaseEntity {
	private String original;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "word_id")
	private Set<Translate> translates;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "word_id")
	private Set<WordSource> sources;

	public String getOriginal() {
		return original;
	}

	public Word setOriginal(String original) {
		this.original = original;
		return this;
	}

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
}
