package com.github.bogdanovmn.translator.etl.allitbooks;

import com.github.bogdanovmn.translator.parser.pdf.PdfContent;
import com.github.bogdanovmn.translator.web.orm.BaseEntity;
import org.springframework.util.DigestUtils;

import javax.persistence.*;
import java.io.IOException;
import java.util.Date;

@Entity
@Table(name = "allitebook_download_process")
public class BookDownloadProcess extends BaseEntity {
	@Enumerated(EnumType.STRING)
	private DownloadStatus status;
	private String errorMsg;
	private Date started;
	private Date updated;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meta_id")
	private BookMeta meta;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id")
	private Book book;


	public DownloadStatus getStatus() {
		return status;
	}

	public BookDownloadProcess setStatus(DownloadStatus status) {
		this.status = status;
		return this;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public BookDownloadProcess setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
		return this;
	}

	public Date getStarted() {
		return started;
	}

	public BookDownloadProcess setStarted(Date started) {
		this.started = started;
		return this;
	}

	public Date getUpdated() {
		return updated;
	}

	public BookDownloadProcess setUpdated(Date updated) {
		this.updated = updated;
		return this;
	}

	public BookMeta getMeta() {
		return meta;
	}

	public BookDownloadProcess setMeta(BookMeta meta) {
		this.meta = meta;
		return this;
	}

	public Book getBook() {
		return book;
	}

	public BookDownloadProcess setBook(Book book) {
		this.book = book;
		return this;
	}

	public void start() {
		Date now = new Date();
		started = now;
		updated = now;
		status = DownloadStatus.DONLOADING;
	}

	public Book createBook(byte[] fileBytes) throws IOException {
		String text = new PdfContent(fileBytes).getText();
		book = new Book()
			.setMeta(meta)
			.setCreated(new Date())
			.setTextSize(text.length())
			.setFileHash(
				DigestUtils.md5DigestAsHex(fileBytes)
			)
			.setText(text);

		return book;
	}

	public void done() {
		status = DownloadStatus.DONE;
		updated = new Date();
	}

	public void error(String message) {
		status = DownloadStatus.ERROR;
		errorMsg = message;
		updated = new Date();
	}
}
