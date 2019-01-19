package com.github.bogdanovmn.translator.web.app.worddefinition;

import com.github.bogdanovmn.translator.web.orm.WordDefinition;

import java.util.List;

public interface WordDefinitionService {
	List<WordDefinition> definitions(String word);
}
