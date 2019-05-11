package com.github.bogdanovmn.translator.service.oxforddictionaries;

import com.github.bogdanovmn.httpclient.core.ResponseException;
import com.github.bogdanovmn.translator.core.definition.DefinitionInstance;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseAnotherWordFormException extends ResponseException {
	private final String word;
	private final List<DefinitionInstance> definitions;

	public ResponseAnotherWordFormException(String word, List<DefinitionInstance> definitions) {
		super(String.format("Another word form: '%s'", word));
		this.word = word;
		this.definitions = definitions;
	}
}
