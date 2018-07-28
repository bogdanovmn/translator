package com.github.bogdanovmn.translator.etl.allitbooks;

import com.github.bogdanovmn.httpclient.simple.SimpleHttpClient;
import com.google.common.io.ByteStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

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
			List<String> uriParts = Arrays.asList(downloadProcess.getMeta().getPdfUrl().split("/"));
			uriParts.set(
				uriParts.size() - 1,
				URLEncoder.encode(
					uriParts.get(uriParts.size() - 1),
					"UTF-8"
				).replaceAll("\\+", "%20")
			);
			try(
				InputStream pdfStream = httpClient.downloadFile(
					uriParts.stream().collect(Collectors.joining("/"))
				)
			) {
				downloadProcess.createBook(
					ByteStreams.toByteArray(pdfStream)
				);
			}
		} catch (Exception e) {
			downloadProcess.error(e.getMessage());
			throw new RuntimeException(e);
		}
		downloadProcess.done();
		LOG.info("done");
		return downloadProcess;
	}
}
