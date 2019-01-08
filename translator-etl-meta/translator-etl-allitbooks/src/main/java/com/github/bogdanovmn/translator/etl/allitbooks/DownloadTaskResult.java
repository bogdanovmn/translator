package com.github.bogdanovmn.translator.etl.allitbooks;

import com.github.bogdanovmn.translator.etl.allitbooks.orm.BookDownloadProcess;
import lombok.Getter;

import java.io.InputStream;

@Getter

class DownloadTaskResult {
	private final BookDownloadProcess downloadProcess;
	private final InputStream pdfStream;

	public DownloadTaskResult(BookDownloadProcess downloadProcess, InputStream pdfStream) {
		this.downloadProcess = downloadProcess;
		this.pdfStream = pdfStream;
	}
}
