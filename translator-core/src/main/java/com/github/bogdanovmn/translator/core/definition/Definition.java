package com.github.bogdanovmn.translator.core.definition;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public
class Definition {
	String word;
	List<DefinitionInstance> instances;
}
