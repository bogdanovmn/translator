package com.github.bogdanovmn.translator.etl.allitbooks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class DownloadService {
	private static final Logger LOG = LoggerFactory.getLogger(DownloadService.class);

	private final static int ERRORS_LIMIT = 20;

	private final BookMetaRepository bookMetaRepository;
	private final BookDownloadProcessRepository bookDownloadProcessRepository;

	public DownloadService(BookMetaRepository bookMetaRepository, BookDownloadProcessRepository bookDownloadProcessRepository) {
		this.bookMetaRepository = bookMetaRepository;
		this.bookDownloadProcessRepository = bookDownloadProcessRepository;
	}

	public void download() throws InterruptedException {
		ExecutorService workers = Executors.newFixedThreadPool(1);
		CompletionService<BookDownloadProcess> completionService = new ExecutorCompletionService<>(workers);

		List<BookDownloadProcess> waitingBooks = nextBatch();
		int errors = 0;

		while (waitingBooks != null) {
			waitingBooks.stream()
				.peek(processItem -> LOG.info("Prepare download: {}", processItem.getMeta().getPdfUrl()))
				.map(DownloadTask::new)
				.forEach(completionService::submit);

			for (int i = 0; i < waitingBooks.size(); i++) {
				try {
					BookDownloadProcess process = completionService.take().get();
				}
				catch (ExecutionException e) {
					errors++;
					LOG.error(e.getMessage());
				}
			}
			if (errors < ERRORS_LIMIT) {
				bookDownloadProcessRepository.save(waitingBooks);
				waitingBooks = nextBatch();
			}
			else {
				LOG.warn("Errors limit is reached");
			}
		}

	}

	private List<BookDownloadProcess> nextBatch() {
		List<BookDownloadProcess> batch = bookDownloadProcessRepository.findTop2ByStatus(DownloadStatus.WAIT);
		if (batch.isEmpty()) {
			List<BookMeta> meta = bookMetaRepository.findTop10ByDownloadProcessNullAndObsoleteFalse();
			if (!meta.isEmpty()) {
				batch = meta.stream()
					.map(BookMeta::createDownloadProcess)
					.collect(Collectors.toList());
			}
			bookMetaRepository.save(meta);
		}
		bookMetaRepository.flush();
		return batch;
	}
}
