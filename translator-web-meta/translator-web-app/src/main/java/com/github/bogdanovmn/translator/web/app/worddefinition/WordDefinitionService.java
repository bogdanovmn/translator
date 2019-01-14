package com.github.bogdanovmn.translator.web.app.worddefinition;

import com.github.bogdanovmn.translator.web.orm.WordDefinitionInstance;

import java.util.List;

public interface WordDefinitionService {
	List<WordDefinitionInstance> definitions(String word);
}
