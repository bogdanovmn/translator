package com.github.bogdanovmn.translator.core;

public class HttpServiceUnavailableException extends HttpServiceException {
	public HttpServiceUnavailableException(Exception e) {
		super(e);
	}

	public HttpServiceUnavailableException(String msg) {
		super(msg);
	}
}
