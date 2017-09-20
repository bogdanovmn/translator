package com.github.bogdanovmn.translator.core;

import com.github.bogdanovmn.translator.core.TranslateServiceException;

public class TranslateServiceUnknownWordException extends TranslateServiceException {
	public TranslateServiceUnknownWordException(String msg) {
		super(msg);
	}
}
