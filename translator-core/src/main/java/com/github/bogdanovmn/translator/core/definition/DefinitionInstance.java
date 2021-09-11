package com.github.bogdanovmn.translator.core.definition;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class DefinitionInstance {
	String pronunciation;
	@Singular
	List<PartOfSpeech> partOfSpeeches;
}
