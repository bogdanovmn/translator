package com.github.bogdanovmn.translator.etl.allitbooks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
class BookMetaImport {
	private static final Logger LOG = LoggerFactory.getLogger(BookMetaImport.class);

	private final Pattern EDITION_PATTERN = Pattern.compile("^(.*),\\s+(\\w+)\\s+edition", Pattern.CASE_INSENSITIVE);

	@Autowired
	private BookMetaRepository bookMetaRepository;

	@Transactional
	void run() throws IOException {
		LOG.info("Update all meta");
		this.updateAllMeta();
		LOG.info("Find obsolate books editions");
		this.findObsolateEditions();
	}

	private void findObsolateEditions() {
		List<BookMeta> books = this.bookMetaRepository.findAllByOrderByTitle();

		String prevBaseTitle = "";
		Integer maxVersion = 0;
		BookMeta prevBook = null;

		for (BookMeta book : books) {
			String baseTitle = book.getTitle();
			String version = "1";

			Matcher matcher = EDITION_PATTERN.matcher(book.getTitle());
			if (matcher.matches()) {
				baseTitle = matcher.group(1).replaceFirst("\\d{4}", "");
				version = matcher.group(2).toLowerCase();
				if (version.equals("third")) {
					version = "3";
				}
				else if (version.equals("second")) {
					version = "2";
				}
				else if (version.matches("^\\d\\w+$")) {
					version = version.replaceAll("\\D", "");
				}
				else {
					version = "1";
				}
			}

//			LOG.info("[ver={}  baseTitle='{}'  orig='{}']", version, baseTitle, book.getTitle());
			int currentVersion = Integer.valueOf(version);

			if (prevBaseTitle.equals(baseTitle)) {
				if (currentVersion > maxVersion) {
					maxVersion = currentVersion;
					prevBook.setObsolate(true);
					LOG.info("[Obsolate] {}", prevBook.getTitle());
				}
			}
			else {
				maxVersion = currentVersion;
			}

			prevBook = book;
			prevBaseTitle = baseTitle;
		}

		this.bookMetaRepository.save(books);
	}

	private void updateAllMeta() throws IOException {
		Site site = new Site();
		BookMetaIterator bookMetaIterator = site.getBookIterator();
		BookMeta book = bookMetaIterator.next();
		while (bookMetaIterator.hasNext()) {
			if (book != null) {
				LOG.info(book.getOriginalUrl());
				BookMeta persistenBook = bookMetaRepository.findBookByOriginalUrl(book.getOriginalUrl());
				if (persistenBook != null) {
					book.setId(persistenBook.getId());
				}
				try {
					bookMetaRepository.save(book);
					LOG.info(book.getTitle());
				}
				catch (DataIntegrityViolationException e) {
					LOG.error("Dublicate url");
				}
			}
		}
		long totalBooks = bookMetaRepository.count();
		LOG.info("Total books: {}", totalBooks);
	}
}
