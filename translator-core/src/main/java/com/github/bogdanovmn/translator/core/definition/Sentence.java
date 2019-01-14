package com.github.bogdanovmn.translator.core.definition;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Sentence {
	private final String description;
	private final String grammaticalNote;
	private final boolean crossReference;
	@Singular
	private final List<Example> examples;
	@Singular
	private final List<Synonym> synonyms;
	@Singular
	private final List<Sentence> subSentences;
}
