package com.github.bogdanovmn.translator.web.orm;

class UserNativeQuery {
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
		"(" + TO_REMEMBER_WORDS_FREQUENT + " limit 5) UNION (" + TO_REMEMBER_WORDS_RARE + " limit 5)";


	private static final String TO_REMEMBER_WORDS_BY_SOURCE_RARE =
		"select w.*"
			+ " from  word w"
			+ " join word2source ws on ws.word_id = w.id"
			+ " left join user_hold_over_word uhow on w.id = uhow.word_id and uhow.user_id = ?1 "
			+ " left join user_remembered_word urw on w.id = urw.word_id and urw.user_id = ?1 "
			+ " where urw.word_id is NULL "
			+ " and   uhow.word_id is NULL "
			+ " and   w.black_list = 0 "
			+ " and   ws.source_id = ?2"
			+ " order by ws.count ";


	private static final String TO_REMEMBER_WORDS_BY_SOURCE_FREQUENT = TO_REMEMBER_WORDS_BY_SOURCE_RARE + "desc";

	static final String TO_REMEMBER_WORDS_BY_SOURCE =
		"(" + TO_REMEMBER_WORDS_BY_SOURCE_FREQUENT + " limit 5) UNION (" + TO_REMEMBER_WORDS_BY_SOURCE_RARE + " limit 5)";

}
