package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor

@Entity
public class WordDefinition extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "word_id", nullable = false)
	private Word word;

	private String pronunciation;

	private boolean crossReference;

	@OneToOne
	@JoinColumn(name = "part_of_speech_id")
	private WordDefinitionPartOfSpeech partOfSpeech;

	private String partOfSpeechNote;

	@Column(length = 1000)
	private String description;

	private String descriptionNote;

	@Column(length = 1000)
	private String synonyms;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "word_definition_id")
	private List<WordDefinitionExample> examples = new ArrayList<>();
}
