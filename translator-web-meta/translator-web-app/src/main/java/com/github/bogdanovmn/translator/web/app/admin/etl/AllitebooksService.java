package com.github.bogdanovmn.translator.web.app.admin.etl;

import com.github.bogdanovmn.translator.core.CompressedText;
import com.github.bogdanovmn.translator.core.EnglishText;
import com.github.bogdanovmn.translator.etl.allitbooks.orm.BookDownloadProcess;
import com.github.bogdanovmn.translator.etl.allitbooks.orm.BookDownloadProcessRepository;
import com.github.bogdanovmn.translator.etl.allitbooks.orm.DownloadStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
class AllitebooksService {
	private final BookDownloadProcessRepository downloadProcessRepository;

	@Autowired
	AllitebooksService(BookDownloadProcessRepository downloadProcessRepository) {
		this.downloadProcessRepository = downloadProcessRepository;
	}

	Map<String, Object> downloadProcessBrief() {
		return new HashMap<String, Object>() {{
			put("statistic", downloadProcessRepository.statusStatistic());
			put("active", downloadProcessRepository.findAllByStatusIsNot(DownloadStatus.DONE));
		}};
	}

	String downloadProcessData(Integer id) throws IOException {
		BookDownloadProcess downloadProcess = downloadProcessRepository.findOne(id);
		if (downloadProcess != null) {
			return new EnglishText(
				new CompressedText(
					downloadProcess.getBook().getData()
				).decompress()
			).words().toString();
		}
		return "";
	}
}
