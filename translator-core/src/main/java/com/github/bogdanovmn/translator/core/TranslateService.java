package com.github.bogdanovmn.translator.core;

import java.util.Set;

public interface TranslateService {
	Set<String> translate(String phrase) throws TranslateServiceException;
}
