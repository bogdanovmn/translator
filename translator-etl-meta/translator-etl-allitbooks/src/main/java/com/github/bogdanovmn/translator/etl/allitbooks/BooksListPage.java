package com.github.bogdanovmn.translator.etl.allitbooks;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class BooksListPage extends HtmlPage {
	private static final Pattern PATTERN_PAGES_COUNT = Pattern.compile("^\\s*\\d+ / (\\d+) Pages\\s*$");
	private final int pageNumber;

	BooksListPage(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	int getPagesTotal()
		throws IOException
	{
		int result = 0;

		Element pagingElement = this.getHtmlDocument()
			.select("div[class^=pagination] span[class=pages]")
			.first();

		if (pagingElement != null) {
			String rawResult = pagingElement.text();
			Matcher matcher = PATTERN_PAGES_COUNT.matcher(rawResult);
			if (matcher.matches()) {
				result = Integer.valueOf(matcher.group(1));
			}
		}
		else {
			throw new IOException("Paging info not found");
		}
		return result;
	}

	Set<BookPageLink> getBookLinks()
		throws IOException
	{
		Set<BookPageLink> result = new HashSet<>();
		Elements articleElements = this.getHtmlDocument().select("article");
		if (!articleElements.isEmpty()) {
			for (Element article : articleElements) {
				Element urlElement = article.select("h2[class=entry-title] a").first();
				String url = urlElement.attr("href");
				String title = urlElement.text();
				result.add(
					new BookPageLink(url, title)
				);
			}
		}
		return result;
	}

	@Override
	String pageUrl() {
		return String.format(
			"%s/page/%d/",
			Site.PREFIX, this.pageNumber
		);
	}
}
