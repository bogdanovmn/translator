package com.github.bogdanovmn.translator.web.orm;

public class SourceWithUserStatistic {
	private final Source source;
	private final Integer userWordsRememberedCount;

	public SourceWithUserStatistic(
		Integer sourceId,
		String sourceRawName,
		String sourceAuthor,
		String sourceTitle,
		Integer sourceWordsCount,
		Integer userWordsRememberedCount
	) {
		this.source = new Source(sourceId)
			.setRawName(sourceRawName)
			.setAuthor(sourceAuthor)
			.setTitle(sourceTitle)
			.setWordsCount(sourceWordsCount);

		this.userWordsRememberedCount = userWordsRememberedCount;
	}
}
