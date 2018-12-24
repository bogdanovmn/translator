package com.github.bogdanovmn.translator.etl.allitbooks.orm;

import com.github.bogdanovmn.translator.orm.core.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter

@Entity
@Table(name = "allitebook_meta")
public class BookMeta extends BaseEntity {

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

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "download_process_id")
	private BookDownloadProcess downloadProcess;

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

	public BookDownloadProcess createDownloadProcess() {
		downloadProcess = new BookDownloadProcess()
			.setMeta(this)
			.setStatus(DownloadStatus.WAIT);

		return downloadProcess;
	}
}
