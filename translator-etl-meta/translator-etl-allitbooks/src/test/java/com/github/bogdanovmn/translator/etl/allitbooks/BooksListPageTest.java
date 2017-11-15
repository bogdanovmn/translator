package com.github.bogdanovmn.translator.etl.allitbooks;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

@Ignore
public class BooksListPageTest {
	@Test
	public void getPagesTotal() throws Exception {
		BooksListPage page = new BooksListPage(1);
		assertEquals(736, page.getPagesTotal());
	}

	@Test
	public void pageUrl() throws Exception {
		BooksListPage page = new BooksListPage(11);
		assertEquals("http://www.allitebooks.com/page/11/", page.pageUrl());
	}

	@Test
	public void getBookPages() throws Exception {
		BooksListPage page = new BooksListPage(1);
		Set<BookPageLink> booksLinks = page.getBookLinks();
		assertEquals(10, booksLinks.size());
	}
}