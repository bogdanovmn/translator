package com.github.bogdanovmn.translator.web.orm;

class WordSourceNativeQuery {
	private static final String TO_REMEMBER_WORDS_BY_SOURCE_RARE =
		"select ws.*"
			+ " from  word2source ws"
			+ " join word w on w.id = ws.word_id"
			+ " left join user_hold_over_word uhow on ws.word_id = uhow.word_id and uhow.user_id = ?1 "
			+ " left join user_remembered_word urw on ws.word_id = urw.word_id and urw.user_id = ?1 "
			+ " where urw.word_id is NULL "
			+ " and   uhow.word_id is NULL "
			+ " and   w.black_list = 0 "
			+ " and   ws.source_id = ?2"
			+ " order by ws.count ";


	private static final String TO_REMEMBER_WORDS_BY_SOURCE_FREQUENT = TO_REMEMBER_WORDS_BY_SOURCE_RARE + "desc";

	static final String TO_REMEMBER_WORDS_BY_SOURCE =
		"(" + TO_REMEMBER_WORDS_BY_SOURCE_FREQUENT + " limit 7) UNION (" + TO_REMEMBER_WORDS_BY_SOURCE_RARE + " limit 3)";

}
