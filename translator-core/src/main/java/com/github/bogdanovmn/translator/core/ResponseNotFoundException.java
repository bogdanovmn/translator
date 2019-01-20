package com.github.bogdanovmn.translator.core;

public class ResponseNotFoundException extends ResponseException {
	public ResponseNotFoundException(String msg) {
		super(msg);
	}

	public ResponseNotFoundException(Exception e) {
		super(e);
	}
}
