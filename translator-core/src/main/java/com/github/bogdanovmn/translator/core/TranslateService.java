package com.github.bogdanovmn.translator.core;

import com.github.bogdanovmn.translator.core.exception.TranslateServiceException;

import java.io.Closeable;

public interface TranslateService extends Closeable {
	String translate(String phrase) throws TranslateServiceException;
}
