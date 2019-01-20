package com.github.bogdanovmn.translator.core;

public class ResponseException extends HttpServiceException {
	public ResponseException(String msg) {
		super(msg);
	}

	public ResponseException(Exception e) {
		super(e);
	}
}
