package com.github.bogdanovmn.translator.core.definition;

import java.util.List;

public interface WordDefinitionService {
	List<DefinitionInstance> definitions(String word) throws WordDefinitionServiceException;
}
