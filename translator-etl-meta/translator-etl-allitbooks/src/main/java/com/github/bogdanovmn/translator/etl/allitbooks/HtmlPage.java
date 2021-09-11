package com.github.bogdanovmn.translator.etl.allitbooks;

import com.github.bogdanovmn.httpclient.diskcache.UrlContentDiskCache;
import com.github.bogdanovmn.httpclient.simple.SimpleHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

abstract class HtmlPage {
	private Document htmlDocument;
	private static final UrlContentDiskCache CACHE = new UrlContentDiskCache(
		new SimpleHttpClient(),
		"TranslatorEtl"
	);

	abstract String pageUrl();

	protected Document getHtmlDocument() throws IOException {
		if (this.htmlDocument == null) {
			String html = CACHE.get(
				new URL(this.pageUrl())
			);
			this.htmlDocument = Jsoup.parse(html);
		}
		return this.htmlDocument;
	}
}
