package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntityWithUniqueName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor

@Entity
@NamedNativeQueries({
	@NamedNativeQuery(
		name = "Word.allBySourceForCloud",
		resultClass = Word.class,
		query = WordCloudNativeQuery.ALL_BY_SOURCE
	),
	@NamedNativeQuery(
		name = "Word.allRememberedForCloud",
		resultClass = Word.class,
		query = WordCloudNativeQuery.REMEMBERED
	),
	@NamedNativeQuery(
		name = "Word.allUnknownForCloud",
		resultClass = Word.class,
		query = WordCloudNativeQuery.UNKNOWN
	),
	@NamedNativeQuery(
		name = "Word.allRememberedBySourceForCloud",
		resultClass = Word.class,
		query = WordCloudNativeQuery.REMEMBERED_BY_SOURCE
	),
	@NamedNativeQuery(
		name = "Word.allUnknownBySourceForCloud",
		resultClass = Word.class,
		query = WordCloudNativeQuery.UNKNOWN_BY_SOURCE
	)
})
public class Word extends BaseEntityWithUniqueName {
	private int frequency;
	private int sourcesCount;

	@Column(nullable = false)
	private boolean blackList = false;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "word")
	private List<WordDefinition> definitions = new ArrayList<>();

	@OneToMany(mappedBy = "word")
	private Set<WordSource> wordSources;

	public Word(String name) {
		super(name);
	}

	public Word(Integer id) {
		super(id);
	}

	public Word incFrequency(int incValue) {
		this.frequency += incValue;
		return this;
	}

	public Word incSourceCount() {
		sourcesCount++;
		return this;
	}
}
