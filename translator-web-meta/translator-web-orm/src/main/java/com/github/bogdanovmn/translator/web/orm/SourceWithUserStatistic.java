package com.github.bogdanovmn.translator.web.orm;

public class SourceWithUserStatistic {
	private final Source source;
	private final Long rememberedCount;

	public SourceWithUserStatistic(Source source, Long rememberedCount) {
		this.source = source;
		this.rememberedCount = rememberedCount;
	}
}
