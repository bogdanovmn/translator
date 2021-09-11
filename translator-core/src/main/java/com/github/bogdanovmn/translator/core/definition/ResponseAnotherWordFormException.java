package com.github.bogdanovmn.translator.core.definition;

import com.github.bogdanovmn.httpclient.core.ResponseException;
import lombok.Getter;

@Getter
public class ResponseAnotherWordFormException extends ResponseException {
	private final Definition definition;

	public ResponseAnotherWordFormException(Definition definition) {
		super(String.format("Another word form: '%s'", definition.word()));
		this.definition = definition;
	}
}
