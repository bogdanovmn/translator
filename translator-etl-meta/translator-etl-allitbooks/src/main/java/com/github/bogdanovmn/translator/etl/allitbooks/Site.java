package com.github.bogdanovmn.translator.etl.allitbooks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Site {
	private static final Logger LOG = LoggerFactory.getLogger(Site.class);

	static String PREFIX = "http://www.allitebooks.com";

	public Site() {
	}

	BookMetaIterator getBookIterator() throws IOException {
		return new BookMetaIterator(
			this.getPagesCount()
		);
	}

	public int getPagesCount() throws IOException {
		BooksListPage page = new BooksListPage(1);
		return page.getPagesTotal();
	}
}
