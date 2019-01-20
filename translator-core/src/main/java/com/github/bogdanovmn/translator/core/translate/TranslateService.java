package com.github.bogdanovmn.translator.core.translate;

import com.github.bogdanovmn.translator.core.HttpServiceException;

import java.util.Set;

public interface TranslateService {
	Set<String> translate(String phrase) throws HttpServiceException;
}
