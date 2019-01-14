package com.github.bogdanovmn.translator.web.orm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor

@Entity
public class WordDefinitionInstancePartOfSpeech {
	private WordDefinitionPartOfSpeech partOfSpeech;

	private String description;

	@ManyToMany
	@JoinTable(
		name = "synonym2wd_instance_part_of_speech",
		joinColumns = @JoinColumn(name = "wd_instance_part_of_speech_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "synonym_id", referencedColumnName = "id")
	)
	private List<WordDefinitionSynonym> synonyms;

	@OneToMany
	@JoinColumn(name = "wd_instance_part_of_speech_id")
	private List<WordDefinitionExampleSentences> exampleSentences;
}
