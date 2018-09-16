package com.github.bogdanovmn.translator.web.app.admin.export;

import com.github.bogdanovmn.translator.web.orm.EntityFactory;
import com.github.bogdanovmn.translator.web.orm.Word;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class ExportWordCache {
	private final EntityFactory entityFactory;

	private final Map<Integer, String> wordNameByExportId;
	private final Map<Integer, Word> wordByExportId = new HashMap<>();

	ExportWordCache(List<ImportSchema.ImportWord> words, EntityFactory entityFactory) {
		wordNameByExportId = words.stream()
			.collect(Collectors.toMap(
				ImportSchema.ImportWord::getId,
				ImportSchema.ImportWord::getName
			)
		);
		this.entityFactory = entityFactory;
	}

	Word getByExportId(int id) {
		Word word = wordByExportId.get(id);
		if (word == null) {
			wordByExportId.put(
				id,
				(Word) entityFactory.getPersistBaseEntityWithUniqueName(
					new Word(wordNameByExportId.get(id))
				)
			);
		}
		return wordByExportId.get(id);
	}
}
