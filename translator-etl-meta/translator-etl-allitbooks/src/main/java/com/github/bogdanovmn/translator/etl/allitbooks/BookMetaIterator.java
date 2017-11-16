package com.github.bogdanovmn.translator.etl.allitbooks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class BookMetaIterator implements Iterator<BookMeta> {
	private final int totalPages;
	private int currentPage = 1;
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
			return null;
		}
	}
}
