package com.github.bogdanovmn.translator.web.orm;

class WordNativeQuery {
	private static final String TO_REMEMBER_WORDS_RARE =
		"select w.*"
			+ " from  word w"
			+ " left join user_hold_over_word uhow on w.id = uhow.word_id and uhow.user_id = ?1 "
			+ " left join user_remembered_word urw on w.id = urw.word_id and urw.user_id = ?1 "
			+ " where urw.word_id is NULL "
			+ " and   uhow.word_id is NULL "
			+ " and   w.black_list = 0 "
			+ " order by w.sources_count desc, w.frequence ";

	private static final String TO_REMEMBER_WORDS_FREQUENT = TO_REMEMBER_WORDS_RARE + "desc";

	static final String TO_REMEMBER_WORDS =
		"(" + TO_REMEMBER_WORDS_FREQUENT + " limit 9) UNION (" + TO_REMEMBER_WORDS_RARE + " limit 1)";

	static final String KNOWN_WORDS =
		"SELECT w.* " +
			"FROM word w " +
			"JOIN user_remembered_word urw on w.id = urw.word_id AND urw.user_id = ?1 " +
			"WHERE w.black_list = 0";

	static final String KNOWN_WORDS_BY_SOURCE =
		"SELECT w.* " +
			"FROM word w " +
			"JOIN user_remembered_word urw on w.id = urw.word_id AND urw.user_id = ?1 " +
			"JOIN word2source ws ON ws.word_id = w.id AND ws.source_id = ?2 " +
			"WHERE w.black_list = 0";

	static final String UNKNOWN_WORDS =
		"SELECT w.* " +
			"FROM word w " +
			"LEFT JOIN user_remembered_word urw on w.id = urw.word_id AND urw.user_id = ?1 " +
			"WHERE urw.word_id IS NULL " +
			"AND w.black_list = 0";

	static final String UNKNOWN_WORDS_BY_SOURCE =
		"SELECT w.* " +
			"FROM word w " +
			"LEFT JOIN user_remembered_word urw on w.id = urw.word_id AND urw.user_id = ?1 " +
			"JOIN word2source ws ON ws.word_id = w.id AND ws.source_id = ?2 " +
			"WHERE urw.word_id IS NULL " +
			"AND w.black_list = 0";
}
