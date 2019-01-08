package com.github.bogdanovmn.translator.web.app.admin.etl;

import com.github.bogdanovmn.translator.core.CompressedText;
import com.github.bogdanovmn.translator.core.EnglishText;
import com.github.bogdanovmn.translator.etl.allitbooks.orm.BookDownloadProcess;
import com.github.bogdanovmn.translator.etl.allitbooks.orm.BookDownloadProcessRepository;
import com.github.bogdanovmn.translator.etl.allitbooks.orm.DownloadStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
class AllitebooksService {
	private final BookDownloadProcessRepository downloadProcessRepository;

	@Autowired
	AllitebooksService(BookDownloadProcessRepository downloadProcessRepository) {
		this.downloadProcessRepository = downloadProcessRepository;
	}

	List<BookDownloadProcessRepository.DownloadStatusStatistic> statistic() {
		return downloadProcessRepository.statusStatistic();
	}

	List<BookDownloadProcess> activeItems() {
		return downloadProcessRepository.findAllByStatusIsNotInOrderByUpdatedDesc(
			Arrays.asList(
				DownloadStatus.DONE,
				DownloadStatus.STUCK,
				DownloadStatus.ERROR
			)
		);
	}

	String downloadProcessData(Integer id) throws IOException {
		Optional<BookDownloadProcess> downloadProcess = downloadProcessRepository.findById(id);
		String result = "";
		if (downloadProcess.isPresent()) {
			result = new EnglishText(
				new CompressedText(
					downloadProcess.get().getBook().getData()
				).decompress()
			).words().toString();
		}
		return result;
	}

	List<BookDownloadProcess> itemsByStatus(DownloadStatus status) {
		return downloadProcessRepository.findAllByStatusOrderByUpdatedDesc(status);
	}
}
