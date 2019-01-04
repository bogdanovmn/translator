package com.github.bogdanovmn.translator.etl.allitbooks;

import com.github.bogdanovmn.translator.etl.allitbooks.orm.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
@Slf4j
class DownloadService {

	private final static int ERRORS_LIMIT = 10;

	private final BookMetaRepository bookMetaRepository;
	private final BookDownloadProcessRepository bookDownloadProcessRepository;

	@Value("${http-proxy}")
	private String httpProxy;

	DownloadService(BookMetaRepository bookMetaRepository, BookDownloadProcessRepository bookDownloadProcessRepository) {
		this.bookMetaRepository = bookMetaRepository;
		this.bookDownloadProcessRepository = bookDownloadProcessRepository;
	}

	void download(int threads) throws InterruptedException {
		LOG.info("Start download with {} threads", threads);

		ExecutorService workers = Executors.newFixedThreadPool(threads);
		CompletionService<BookDownloadProcess> completionService = new ExecutorCompletionService<>(workers);

		List<BookDownloadProcess> waitingBooks = nextBatch();
		int errors = 0;

		while (waitingBooks != null) {
			waitingBooks.stream()
				.peek(processItem ->
					LOG.info("Prepare download {}Mb: {}",
						processItem.getMeta().getFileSizeMb(),
						processItem.getMeta().getPdfUrl()
					)
				)
				.map(x -> new DownloadTask(x, httpProxy))
				.forEach(completionService::submit);

			for (int i = 0; i < waitingBooks.size(); i++) {
				try {
					BookDownloadProcess process = completionService.take().get();
				}
				catch (ExecutionException e) {
					errors++;
					LOG.error(e.getMessage(), e);
				}
			}
			if (errors < ERRORS_LIMIT) {
				bookDownloadProcessRepository.saveAll(waitingBooks);
				waitingBooks = nextBatch();
			}
			else {
				LOG.warn("Errors limit is reached");
				break;
			}
		}
	}

	private List<BookDownloadProcess> nextBatch() {
		List<BookDownloadProcess> batch = bookDownloadProcessRepository.findTop10ByStatus(DownloadStatus.WAIT);
		if (batch.isEmpty()) {
			List<BookMeta> meta = bookMetaRepository.findTop10ByDownloadProcessNullAndObsoleteFalse();
			if (!meta.isEmpty()) {
				batch = meta.stream()
					.map(BookMeta::createDownloadProcess)
					.collect(Collectors.toList());
			}
			bookMetaRepository.saveAll(meta);
		}
		bookMetaRepository.flush();
		return batch;
	}
}
