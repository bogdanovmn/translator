package com.github.bogdanovmn.translator.web.app.cloud;

enum CloudContentFilterToggle {
	ALL("Все", new AllCloudWordsRepository()),
	UNKNOWN("Неизученные", new UnknownCloudWordsRepository()),
	REMEMBERED("Изученные", new RememberedCloudWordsRepository());

	private final String title;
	private final CloudWordsRepository repository;


	CloudContentFilterToggle(String title, CloudWordsRepository repository) {
		this.title = title;
		this.repository = repository;
	}

	String title() {
		return title;
	}

	CloudWordsRepository repository() {
		return repository;
	}


	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}
