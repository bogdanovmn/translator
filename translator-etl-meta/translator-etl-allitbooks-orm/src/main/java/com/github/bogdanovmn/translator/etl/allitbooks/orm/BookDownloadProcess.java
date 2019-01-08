package com.github.bogdanovmn.translator.etl.allitbooks.orm;

import com.github.bogdanovmn.translator.orm.core.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter

@Entity
@Table(
	name = "allitebook_download_process",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = "meta_id"),
		@UniqueConstraint(columnNames = "book_id")
	})
public class BookDownloadProcess extends BaseEntity {
	@Enumerated(EnumType.STRING)
	private DownloadStatus status;
	private String errorMsg;
	private Date started;
	private Date updated;

	@OneToOne
	@JoinColumn(name = "meta_id")
	private BookMeta meta;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "book_id")
	private Book book;


	public void start() {
		Date now = new Date();
		started = now;
		updated = now;
		status = DownloadStatus.DOWNLOADING;
	}

	public void downloaded() {
		status = DownloadStatus.DOWNLOADED;
		updated = new Date();
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
