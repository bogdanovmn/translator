package com.github.bogdanovmn.translator.core.exception;

import java.io.IOException;

public class TranslateServiceUnavailableException extends TranslateServiceException {
	public TranslateServiceUnavailableException(IOException e) {
		super(e);
	}

	public TranslateServiceUnavailableException(String msg) {
		super(msg);
	}
}
