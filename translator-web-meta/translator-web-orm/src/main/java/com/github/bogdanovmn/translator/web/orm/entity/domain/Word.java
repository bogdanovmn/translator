package com.github.bogdanovmn.translator.web.orm.entity.domain;

import com.github.bogdanovmn.translator.web.orm.entity.common.BaseEntity;
import com.github.bogdanovmn.translator.web.orm.entity.common.BaseEntityWithUniqueName;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Word extends BaseEntityWithUniqueName {
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "word_id")
	private Set<Translate> translates;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "word_id")
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
}
