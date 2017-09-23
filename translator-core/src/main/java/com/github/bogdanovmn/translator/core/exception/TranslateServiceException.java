package com.github.bogdanovmn.translator.core.exception;

import java.io.IOException;

public class TranslateServiceException extends Exception {
	public TranslateServiceException(String msg) {
		super(msg);
	}

	public TranslateServiceException(IOException e) {
		super(e);
	}
}
