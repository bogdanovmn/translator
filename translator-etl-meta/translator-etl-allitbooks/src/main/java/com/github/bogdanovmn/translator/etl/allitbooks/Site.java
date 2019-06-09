package com.github.bogdanovmn.translator.etl.allitbooks;

import java.io.IOException;

class Site {
	static String PREFIX = "http://www.allitebooks.com";

	Site() {
	}

	BookMetaIterator getBookIterator() throws IOException {
		return new BookMetaIterator(
			getPagesCount()
		);
	}

	private int getPagesCount() throws IOException {
		BooksListPage page = new BooksListPage(1);
		return page.getPagesTotal();
	}
}
