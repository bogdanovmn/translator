package com.github.bogdanovmn.translator.core.definition;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PartOfSpeech {
	private final String name;
	private final String grammaticalNote;
	@Singular
	private final List<Sentence> sentences;
}
