package com.github.bogdanovmn.translator.etl.allitbooks;

import com.github.bogdanovmn.httpclient.simple.SimpleHttpClient;
import com.google.common.io.ByteStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.concurrent.Callable;

class DownloadTask implements Callable<BookDownloadProcess> {
	private static final Logger LOG = LoggerFactory.getLogger(DownloadTask.class);

	private final SimpleHttpClient httpClient = new SimpleHttpClient("");
	private final BookDownloadProcess downloadProcess;

	DownloadTask(BookDownloadProcess downloadProcess) {
		this.downloadProcess = downloadProcess;
	}

	@Override
	public BookDownloadProcess call() throws Exception {
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
		} catch (Exception e) {
			downloadProcess.error(e.getMessage());
			LOG.error(e.getMessage());
			throw new RuntimeException(e);
		}
		downloadProcess.done();
		LOG.info("done");
		return downloadProcess;
	}
}
