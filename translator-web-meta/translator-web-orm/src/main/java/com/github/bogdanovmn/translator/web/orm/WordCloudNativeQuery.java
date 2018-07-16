package com.github.bogdanovmn.translator.web.orm;

class WordCloudNativeQuery {
	private static final String WORD_BY_SOURCE_FOR_FIELDS =
		"w.id, w.name, ws.count frequence, w.black_list, w.sources_count ";

	static final String ALL_BY_SOURCE =
		"SELECT " + WORD_BY_SOURCE_FOR_FIELDS +
			" FROM word w " +
			" JOIN word2source ws ON ws.word_id = w.id AND ws.source_id = ?1 " +
			" WHERE w.black_list = 0";

	static final String REMEMBERED =
		"SELECT w.* " +
			"FROM word w " +
			"JOIN user_remembered_word urw on w.id = urw.word_id AND urw.user_id = ?1 " +
			"WHERE w.black_list = 0";

	static final String REMEMBERED_BY_SOURCE =
		"SELECT " + WORD_BY_SOURCE_FOR_FIELDS +
			" FROM word w " +
			" JOIN user_remembered_word urw on w.id = urw.word_id AND urw.user_id = ?1 " +
			" JOIN word2source ws ON ws.word_id = w.id AND ws.source_id = ?2 " +
			" WHERE w.black_list = 0";

	static final String UNKNOWN =
		"SELECT w.* " +
			"FROM word w " +
			"LEFT JOIN user_remembered_word urw on w.id = urw.word_id AND urw.user_id = ?1 " +
			"WHERE urw.word_id IS NULL " +
			"AND w.black_list = 0";

	static final String UNKNOWN_BY_SOURCE =
		"SELECT " + WORD_BY_SOURCE_FOR_FIELDS +
			" FROM word w " +
			" LEFT JOIN user_remembered_word urw on w.id = urw.word_id AND urw.user_id = ?1 " +
			" JOIN word2source ws ON ws.word_id = w.id AND ws.source_id = ?2 " +
			" WHERE urw.word_id IS NULL " +
			" AND w.black_list = 0";
}
