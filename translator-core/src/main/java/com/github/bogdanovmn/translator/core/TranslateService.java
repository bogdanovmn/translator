package com.github.bogdanovmn.translator.core;

import java.io.Closeable;
import java.util.Set;

public interface TranslateService extends Closeable {
	Set<String> translate(String phrase) throws TranslateServiceException;
}
