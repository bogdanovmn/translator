package com.github.bogdanovmn.translator.core;

import java.io.Closeable;

public interface TranslateService extends Closeable {
	String translate(String phrase) throws TranslateServiceException;
}
