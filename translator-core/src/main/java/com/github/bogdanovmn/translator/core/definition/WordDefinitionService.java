package com.github.bogdanovmn.translator.core.definition;

import com.github.bogdanovmn.httpclient.core.HttpServiceException;

import java.util.List;

public interface WordDefinitionService {
	List<DefinitionInstance> definitions(String word) throws HttpServiceException;
}
