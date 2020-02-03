package com.github.bogdanovmn.translator.web.app.words;

enum SortBy {
	name,
	frequency;

	static SortBy getOrDefault(String value) {
		try {
			return SortBy.valueOf(value);
		}
		catch (Exception e) {
			return name;
		}
	}
}
