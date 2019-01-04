package com.github.bogdanovmn.translator.etl.allitbooks;

import com.github.bogdanovmn.translator.etl.allitbooks.orm.*;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
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

	synchronized void download(int threads) throws InterruptedException {
		LOG.info("Start download with {} threads", threads);

		ExecutorService workers = Executors.newFixedThreadPool(threads);
		CompletionService<BookDownloadProcess> completionService = new ExecutorCompletionService<>(workers);

		List<BookDownloadProcess> waitingBooks = initialBatch();
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
			bookDownloadProcessRepository.saveAll(waitingBooks);
			if (errors < ERRORS_LIMIT) {
				waitingBooks = nextBatch();
			}
			else {
				LOG.warn("Errors limit is reached");
				break;
			}
		}
		workers.shutdown();
	}

	private List<BookDownloadProcess> initialBatch() {
		List<BookDownloadProcess> result;
		List<BookDownloadProcess> prevProcesses = bookDownloadProcessRepository.findAllByStatusIsNotIn(
			Arrays.asList(
				DownloadStatus.DONE, DownloadStatus.ERROR
			)
		);
		if (prevProcesses.isEmpty()) {
			LOG.info("No previous processes found. Getting next batch");
			result = nextBatch();
		}
		else {
			LOG.info("{} previous processes found. Restarting them", prevProcesses.size());
			result = prevProcesses.stream()
				.map(process -> process.setStatus(DownloadStatus.WAIT))
				.collect(Collectors.toList());
		}
		return result;
	}

	private List<BookDownloadProcess> nextBatch() {
		List<BookDownloadProcess> batch;
		List<BookMeta> meta = bookMetaRepository.findTop10ByDownloadProcessNullAndObsoleteFalse();
		if (meta.isEmpty()) {
			batch = Collections.emptyList();
		}
		else {
			batch = meta.stream()
				.map(BookMeta::createDownloadProcess)
				.collect(Collectors.toList());
		}
		bookMetaRepository.saveAll(meta);
		bookMetaRepository.flush();
		return batch;
	}
}
