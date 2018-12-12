package com.github.bogdanovmn.translator.core;

public class HttpServiceException extends Exception {
	public HttpServiceException(String msg) {
		super(msg);
	}

	public HttpServiceException(Exception e) {
		super(e);
	}
}
