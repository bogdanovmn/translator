package com.github.bogdanovmn.translator.etl.allitbooks;

import com.github.bogdanovmn.translator.etl.allitbooks.orm.BookMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

class BookMetaIterator implements Iterator<BookMeta> {
	private static final Logger LOG = LoggerFactory.getLogger(BookMetaIterator.class);

	private final int totalPages;
	private int currentPage = 0;
	private final List<BookPageLink> currentPageBooksLinks = new ArrayList<>();

	BookMetaIterator(int totalPages) {
		this.totalPages = totalPages;
	}

	@Override
	public boolean hasNext() {
		return !this.currentPageBooksLinks.isEmpty() || (this.currentPage < this.totalPages);
	}

	@Override
	public BookMeta next() {
		if (!hasNext()) {
			throw new NoSuchElementException("Next element not found");
		}

		if (this.currentPageBooksLinks.isEmpty()) {
			try {
				this.currentPageBooksLinks.addAll(
					new BooksListPage(++this.currentPage).getBookLinks()
				);
			}
			catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		try {
			return this.currentPageBooksLinks.remove(0).getBook();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		catch (PureBookMetaException e) {
			LOG.error(e.getMessage());
			return null;
		}
	}
}
