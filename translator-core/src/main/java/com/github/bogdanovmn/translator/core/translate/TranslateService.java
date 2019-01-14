package com.github.bogdanovmn.translator.core.translate;

import java.util.Set;

public interface TranslateService {
	Set<String> translate(String phrase) throws TranslateServiceException;
}
