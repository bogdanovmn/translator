package com.github.bogdanovmn.translator.core;

public class ParseResponseException extends HttpServiceException {
	public ParseResponseException(String msg) {
		super(msg);
	}

	public ParseResponseException(Exception e) {
		super(e);
	}
}
