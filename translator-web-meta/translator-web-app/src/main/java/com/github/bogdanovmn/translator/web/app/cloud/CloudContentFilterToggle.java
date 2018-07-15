package com.github.bogdanovmn.translator.web.app.cloud;

enum CloudContentFilterToggle {
	ALL("Все"),
	UNKNOWN("Неизученные"),
	KNOWN("Изученные");

	private final String title;

	CloudContentFilterToggle(final String title) {
		this.title = title;
	}

	String title() {
		return title;
	}

	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}
