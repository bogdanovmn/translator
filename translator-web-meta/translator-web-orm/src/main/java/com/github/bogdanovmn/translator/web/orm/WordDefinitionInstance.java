package com.github.bogdanovmn.translator.web.orm;

import com.github.bogdanovmn.translator.orm.core.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor

@Entity
public class WordDefinitionInstance extends BaseEntity {
	@OneToMany
	@JoinColumn(name = "word_definition_instance_id")
	private List<WordDefinitionInstancePartOfSpeech> partOfSpeeches = new ArrayList<>();
}
