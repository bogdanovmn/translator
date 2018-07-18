package com.github.bogdanovmn.translator.etl.allitbooks;

import com.github.bogdanovmn.httpclient.simple.SimpleHttpClient;
import com.google.common.io.ByteStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class DownloadService {
	private static final Logger LOG = LoggerFactory.getLogger(DownloadService.class);

	private final static int ERRORS_LIMIT = 20;

	private final SimpleHttpClient httpClient = new SimpleHttpClient("");

	private final BookMetaRepository bookMetaRepository;
	private final BookDownloadProcessRepository bookDownloadProcessRepository;
	private final BookRepository bookRepository;

	public DownloadService(BookMetaRepository bookMetaRepository, BookDownloadProcessRepository bookDownloadProcessRepository, BookRepository bookRepository) {
		this.bookMetaRepository = bookMetaRepository;
		this.bookDownloadProcessRepository = bookDownloadProcessRepository;
		this.bookRepository = bookRepository;
	}

	public void download() {
		ExecutorService workers = Executors.newFixedThreadPool(1);

		List<BookDownloadProcess> waitingBooks = nextBatch();
		AtomicInteger errors = new AtomicInteger(0);
		while (waitingBooks != null) {
			waitingBooks.forEach(
				downloadProcess ->
					CompletableFuture.supplyAsync(
						() -> {
							downloadProcess.start();
							try {
								InputStream pdfStream = httpClient.downloadFile(
									downloadProcess.getMeta().getPdfUrl()
								);
								downloadProcess.createBook(
									ByteStreams.toByteArray(pdfStream)
								);
							} catch (Exception e) {
								downloadProcess.error(e.getMessage());
								errors.incrementAndGet();
								throw new RuntimeException(e);
							}
							return downloadProcess;
						},
						workers
					).thenApply(
						completedProcess -> {
							completedProcess.done();
							LOG.info("done");
							return null;
						}
					)
			);

			waitingBooks = nextBatch();
		}

	}

	private List<BookDownloadProcess> nextBatch() {
		List<BookDownloadProcess> batch = bookDownloadProcessRepository.findTop10ByStatusWait();
		if (batch == null) {
			List<BookMeta> meta = bookMetaRepository.findTop10ByDownloadProcessNullAndObsoleteFalse();
			if (meta != null) {
				batch = meta.stream()
					.map(BookMeta::createDownloadProcess)
					.collect(Collectors.toList());
			}
		}
		bookMetaRepository.flush();
		return batch;
	}
}
