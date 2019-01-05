package com.github.bogdanovmn.translator.etl.allitbooks;

import com.github.bogdanovmn.httpclient.simple.SimpleHttpClient;
import com.github.bogdanovmn.translator.etl.allitbooks.orm.Book;
import com.github.bogdanovmn.translator.etl.allitbooks.orm.BookDownloadProcess;
import com.github.bogdanovmn.translator.etl.allitbooks.orm.BookDownloadProcessRepository;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.concurrent.Callable;

@Slf4j
class DownloadTask implements Callable<BookDownloadProcess> {

	private final SimpleHttpClient httpClient;
	private final BookDownloadProcess downloadProcess;
	private final BookDownloadProcessRepository bookDownloadProcessRepository;

	DownloadTask(BookDownloadProcess downloadProcess, String httpProxy, BookDownloadProcessRepository bookDownloadProcessRepository) {
		this.httpClient = SimpleHttpClient.withHttpProxy(httpProxy);
		this.downloadProcess = downloadProcess;
		this.bookDownloadProcessRepository = bookDownloadProcessRepository;
	}

	@Override
	public BookDownloadProcess call() {
		downloadProcess.start();
		bookDownloadProcessRepository.save(downloadProcess);

		try {
			String url = downloadProcess.getMeta().getPdfUrl().replaceAll(" ", "%20");
			LOG.info("Download url: {}", url);
			try (InputStream pdfStream = httpClient.downloadFile(url)) {
				Book book = Book.fromStream(pdfStream)
					.setMeta(downloadProcess.getMeta());
				downloadProcess.setBook(book);
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
