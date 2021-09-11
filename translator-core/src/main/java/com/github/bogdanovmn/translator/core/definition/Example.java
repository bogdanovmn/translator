package com.github.bogdanovmn.translator.core.definition;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Example {
	String value;
	String grammaticalNote;
	boolean primary;
}
