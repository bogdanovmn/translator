package com.github.bogdanovmn.translator.etl.allitbooks;

import com.github.bogdanovmn.translator.etl.allitbooks.orm.BookMeta;
import com.github.bogdanovmn.translator.etl.allitbooks.orm.BookMetaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	private final BookMetaRepository bookMetaRepository;

	@Autowired
	public BookMetaImport(BookMetaRepository bookMetaRepository) {
		this.bookMetaRepository = bookMetaRepository;
	}

	@Transactional(rollbackOn = Exception.class)
	public synchronized void run() throws IOException {
		LOG.info("Update all meta");
		this.updateAllMeta();
		LOG.info("Find obsolete books editions");
		this.findObsoleteEditions();
	}

	private void findObsoleteEditions() {
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
					prevBook.setObsolete(true);
					LOG.info("[Obsolete] {}", prevBook.getTitle());
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
		while (bookMetaIterator.hasNext()) {
			BookMeta book = bookMetaIterator.next();
			if (book != null) {
				LOG.info("Parsed successfully");
				BookMeta persistentBook = bookMetaRepository.findBookByOriginalUrl(book.getOriginalUrl());
				if (persistentBook != null) {
					LOG.info("Book already exists, update it");
					book.setId(persistentBook.getId());
				}
				bookMetaRepository.save(book);
			}
			else {
				LOG.error("Parse error");
			}
		}
		long totalBooks = bookMetaRepository.count();
		LOG.info("Total books: {}", totalBooks);
	}
}
