package com.github.bogdanovmn.translator.core;

public class TranslateServiceException extends Exception {
	public TranslateServiceException(String msg) {
		super(msg);
	}

	public TranslateServiceException(Exception e) {
		super(e);
	}
}
