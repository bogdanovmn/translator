package com.github.bogdanovmn.translator.core.definition;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Example {
	private final String value;
	private final String grammaticalNote;
	private final boolean primary;
}
