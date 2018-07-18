package com.github.bogdanovmn.translator.etl.allitbooks;

import javax.persistence.*;

@Entity
@Table(name = "allitebook_meta")
public class BookMeta {
	@Id
	@GeneratedValue
	private int id;

	@Column(unique = true, nullable = false)
	private String originalUrl;
	private String coverUrl;
	private String title;
	private String author;

	@Transient
	private String language;

	private String category;

	@Column(unique = true, nullable = false)
	private String pdfUrl;
	private int pages;
	private float fileSizeMb;
	private int year;

	private boolean obsolete = false;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "download_process_id")
	private BookDownloadProcess downloadProcess;

	public boolean isObsolete() {
		return obsolete;
	}

	public BookMeta setObsolete(boolean obsolete) {
		this.obsolete = obsolete;
		return this;
	}

	public int getId() {
		return id;
	}

	public BookMeta setId(int id) {
		this.id = id;
		return this;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public BookMeta setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
		return this;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public BookMeta setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public BookMeta setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getAuthor() {
		return author;
	}

	public BookMeta setAuthor(String author) {
		this.author = author;
		return this;
	}

	public String getLanguage() {
		return language;
	}

	public BookMeta setLanguage(String language) {
		this.language = language;
		return this;
	}

	public String getCategory() {
		return category;
	}

	public BookMeta setCategory(String category) {
		this.category = category;
		return this;
	}

	public String getPdfUrl() {
		return pdfUrl;
	}

	public BookMeta setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
		return this;
	}

	public int getPages() {
		return pages;
	}

	public BookMeta setPages(int pages) {
		this.pages = pages;
		return this;
	}

	public float getFileSizeMb() {
		return fileSizeMb;
	}

	public BookMeta setFileSizeMb(float fileSizeMb) {
		this.fileSizeMb = fileSizeMb;
		return this;
	}

	public int getYear() {
		return year;
	}

	public BookMeta setYear(int year) {
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

	BookDownloadProcess createDownloadProcess() {
		downloadProcess = new BookDownloadProcess()
			.setStatus(DownloadStatus.WAIT);

		return downloadProcess;
	}
}
