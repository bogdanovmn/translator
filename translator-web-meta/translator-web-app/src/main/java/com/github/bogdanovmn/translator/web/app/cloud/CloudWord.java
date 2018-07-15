package com.github.bogdanovmn.translator.web.app.cloud;

import com.github.bogdanovmn.translator.web.orm.Word;

class CloudWord {
	private final Word word;
	private final int relativeSizePercent;

	CloudWord(final Word word, final int relativeSizePercent) {
		this.word = word;
		this.relativeSizePercent = relativeSizePercent;
	}

	String color() {
		if (relativeSizePercent < 55) {
			return "darkgray";
		}
		else if (relativeSizePercent < 65) {
			return "dimgray";
		}
		return "black";
	}
}
