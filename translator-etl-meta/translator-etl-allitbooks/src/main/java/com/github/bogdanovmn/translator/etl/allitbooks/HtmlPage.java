package com.github.bogdanovmn.translator.etl.allitbooks;

import com.github.bogdanovmn.downloadwlc.UrlContentDiskCache;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

abstract class HtmlPage {
	private Document htmlDocument;
	private static final UrlContentDiskCache CACHE = new UrlContentDiskCache("TranslatorEtl");

	abstract String pageUrl();

	protected Document getHtmlDocument()
		throws IOException
	{
		if (this.htmlDocument == null) {
			String html = CACHE.getText(
				new URL(this.pageUrl())
			);

			this.htmlDocument = Jsoup.parse(html);
		}

		return this.htmlDocument;
	}
}
