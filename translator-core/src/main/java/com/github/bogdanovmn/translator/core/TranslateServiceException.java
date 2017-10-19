package com.github.bogdanovmn.translator.core;

import java.io.IOException;

public class TranslateServiceException extends Exception {
	public TranslateServiceException(String msg) {
		super(msg);
	}

	public TranslateServiceException(IOException e) {
		super(e);
	}
}
