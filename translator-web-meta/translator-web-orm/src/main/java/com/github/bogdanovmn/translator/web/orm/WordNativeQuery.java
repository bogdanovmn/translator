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
		"(" + TO_REMEMBER_WORDS_FREQUENT + " limit 7) UNION (" + TO_REMEMBER_WORDS_RARE + " limit 3)";
}
