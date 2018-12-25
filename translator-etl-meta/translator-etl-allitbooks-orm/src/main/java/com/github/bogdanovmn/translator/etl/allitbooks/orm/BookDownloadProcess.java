package com.github.bogdanovmn.translator.etl.allitbooks.orm;

import com.github.bogdanovmn.translator.core.CompressedText;
import com.github.bogdanovmn.translator.orm.core.BaseEntity;
import com.github.bogdanovmn.translator.parser.common.DocumentContent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.DigestUtils;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter

@Entity
@Table(name = "allitebook_download_process")
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

	public Book createBook(byte[] fileBytes) throws Exception {
		String text = DocumentContent.fromByteArray(fileBytes).text();

		CompressedText compressedText = CompressedText.from(text);
		book = new Book()
			.setMeta(meta)
			.setCreated(new Date())
			.setTextSize(text.length())
			.setFileHash(
				DigestUtils.md5DigestAsHex(fileBytes)
			)
			.setData(compressedText.bytes());

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
