package com.github.bogdanovmn.translator.core;

import java.util.Set;

public interface WordDefinitionService {
	Set<String> definition(String word) throws WordDefinitionServiceException;
}
