package com.github.bogdanovmn.translator.etl.allitbooks;

class Book {
	private String originalUrl;
	private String coverUrl;
	private String title;
	private String author;
	private String language;
	private String category;
	private String pdfUrl;
	private int pages;
	private float fileSizeMb;
	private int year;

	public String getCoverUrl() {
		return coverUrl;
	}

	public Book setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Book setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getAuthor() {
		return author;
	}

	public Book setAuthor(String author) {
		this.author = author;
		return this;
	}

	public String getLanguage() {
		return language;
	}

	public Book setLanguage(String language) {
		this.language = language;
		return this;
	}

	public String getCategory() {
		return category;
	}

	public Book setCategory(String category) {
		this.category = category;
		return this;
	}

	public String getPdfUrl() {
		return pdfUrl;
	}

	public Book setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
		return this;
	}

	public int getPages() {
		return pages;
	}

	public Book setPages(int pages) {
		this.pages = pages;
		return this;
	}

	public float getFileSizeMb() {
		return fileSizeMb;
	}

	public Book setFileSizeMb(float fileSizeMb) {
		this.fileSizeMb = fileSizeMb;
		return this;
	}

	public int getYear() {
		return year;
	}

	public Book setYear(int year) {
		this.year = year;
		return this;
	}

	@Override
	public String toString() {
		return "Book{" +
			"coverUrl='" + coverUrl + '\'' +
			", title='" + title + '\'' +
			", author='" + author + '\'' +
			", language='" + language + '\'' +
			", category='" + category + '\'' +
			", pdfUrl='" + pdfUrl + '\'' +
			", pages=" + pages +
			", fileSizeMb=" + fileSizeMb +
			", year=" + year +
			'}';
	}
}
