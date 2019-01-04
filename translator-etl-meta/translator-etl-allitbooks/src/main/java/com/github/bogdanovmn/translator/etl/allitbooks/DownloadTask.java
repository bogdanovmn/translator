package com.github.bogdanovmn.translator.etl.allitbooks;

import com.github.bogdanovmn.httpclient.simple.SimpleHttpClient;
import com.github.bogdanovmn.translator.etl.allitbooks.orm.Book;
import com.github.bogdanovmn.translator.etl.allitbooks.orm.BookDownloadProcess;
import com.google.common.io.ByteStreams;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.concurrent.Callable;

@Slf4j
class DownloadTask implements Callable<BookDownloadProcess> {

	private final SimpleHttpClient httpClient;
	private final BookDownloadProcess downloadProcess;

	DownloadTask(BookDownloadProcess downloadProcess, String httpProxy) {
		this.httpClient = SimpleHttpClient.withHttpProxy(httpProxy);
		this.downloadProcess = downloadProcess;
	}

	@Override
	public BookDownloadProcess call() {
		downloadProcess.start();

		try {
			String url = downloadProcess.getMeta().getPdfUrl().replaceAll(" ", "%20");
			LOG.info("Download url: {}", url);
			try (InputStream pdfStream = httpClient.downloadFile(url)) {
				Book book = downloadProcess.createBook(
					ByteStreams.toByteArray(pdfStream)
				);
				LOG.info("Created book: {}", book);
			}
		}
		catch (Exception e) {
			downloadProcess.error(
				String.format("%s%n    <-- %s",
					e.getMessage(),
					e.getCause().getMessage()
				)
			);
			LOG.error(e.getMessage());
			throw new RuntimeException(e);
		}

		downloadProcess.done();
		LOG.info("done");

		return downloadProcess;
	}
}
