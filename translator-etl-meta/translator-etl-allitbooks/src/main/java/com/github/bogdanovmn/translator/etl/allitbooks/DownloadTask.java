package com.github.bogdanovmn.translator.etl.allitbooks;

import com.github.bogdanovmn.httpclient.simple.SimpleHttpClient;
import com.github.bogdanovmn.translator.etl.allitbooks.orm.BookDownloadProcess;
import com.github.bogdanovmn.translator.etl.allitbooks.orm.BookDownloadProcessRepository;
import com.google.common.io.ByteStreams;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.Callable;

@Slf4j
class DownloadTask implements Callable<DownloadTaskResult> {

	private final SimpleHttpClient httpClient;
	private final BookDownloadProcess downloadProcess;
	private final BookDownloadProcessRepository bookDownloadProcessRepository;

	DownloadTask(BookDownloadProcess downloadProcess, String httpProxy, BookDownloadProcessRepository bookDownloadProcessRepository) {
		this.httpClient = SimpleHttpClient.withHttpProxy(httpProxy);
		this.downloadProcess = downloadProcess;
		this.bookDownloadProcessRepository = bookDownloadProcessRepository;
	}

	@Override
	public DownloadTaskResult call() {
		DownloadTaskResult result;
		downloadProcess.start();
		bookDownloadProcessRepository.save(downloadProcess);

		try {
			String url = downloadProcess.getMeta().getPdfUrl().replaceAll(" ", "%20");
			LOG.info("Download url: {}", url);
			InputStream pdfStream = httpClient.downloadFile(url);
			byte[] content = ByteStreams.toByteArray(pdfStream);
			result = new DownloadTaskResult(downloadProcess, new ByteArrayInputStream(content));
			downloadProcess.downloaded();
			LOG.info("downloaded");
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
		finally {
			bookDownloadProcessRepository.save(downloadProcess);
		}

		return result;

	}
}
