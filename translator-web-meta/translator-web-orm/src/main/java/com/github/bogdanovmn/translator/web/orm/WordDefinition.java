package com.github.bogdanovmn.translator.web.orm;

import com.github.bogdanovmn.translator.orm.core.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor

@Entity
public class WordDefinition extends BaseEntity {
	@OneToOne
	@JoinColumn(name = "word_id")
	private Word word;

	private String pronunciation;

	@OneToMany
	@JoinColumn(name = "word_definition_id")
	private List<WordDefinitionInstance> instances = new ArrayList<>();
}
