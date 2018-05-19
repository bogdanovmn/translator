package com.github.bogdanovmn.translator.etl.allitbooks;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class BookPageLink extends HtmlPage {
	private static final Logger LOG = LoggerFactory.getLogger(BookPageLink.class);

	private final String url;
	private final String title;

	BookPageLink(String url, String title) {
		this.url = url;
		this.title = title;
	}

	BookMeta getBook() throws IOException, PureBookMetaException {
		LOG.info("Try to get '{}' [{}]", title, url);

		Element article = this.getHtmlDocument().select("article").first();
		if (article == null) {
			throw new PureBookMetaException("Empty HTML");
		}
		Element detailsElement = article.select("div[class=book-detail] dl").first();
		Element pdfUrlElement = article.select("span[class=download-links] a").first();

		if (pdfUrlElement == null) {
			throw new PureBookMetaException("Pdf url not found");
		}

		Map<String, String> details = this.parseDetails(detailsElement);
		BookMeta book = new BookMeta()
			.setTitle(this.title)
			.setCoverUrl(
				article.select("img[class=attachment-post-thumbnail wp-post-image]").first().attr("src")
			)
			.setAuthor(details.get("Author"))
			.setCategory(details.get("Category"))
			.setFileSizeMb(
				Float.valueOf(
					details.get("File size")
				)
			)
			.setLanguage(details.get("Language"))
			.setPages(Integer.valueOf(details.get("Pages")))
			.setYear(Integer.valueOf(details.get("Year")))
			.setPdfUrl(
				pdfUrlElement.attr("href")
			)
			.setOriginalUrl(this.url);

		return book;
	}

	private Map<String, String> parseDetails(Element detilsElement) {
		Map<String, String> result = new HashMap<>();
		List<String> titles = detilsElement.select("dt").stream()
			.map(x -> x.text().trim().replaceFirst(":", ""))
			.collect(Collectors.toList());
		Elements valueElements = detilsElement.select("dd");

		for (int i = 0; i < titles.size(); i++) {
			String key = titles.get(i);
			String value;
			if (key.equals("Author") || key.equals("Category")) {
				value = valueElements.get(i).select("a").stream()
					.map(x -> x.text().trim())
					.collect(Collectors.joining(", "));
				result.put(key, value);
			}
			else {
				value = valueElements.get(i).text().trim();
				if (key.equals("Year")) {
					Matcher matcher = Pattern.compile("(\\d{4})").matcher(value);
					if (matcher.matches()) {
						value = matcher.group(1);
					}
					else {
						value = "0";
					}
				}

				if (key.equals("File size")) {
					Matcher matcher = Pattern.compile("(\\d+(?:\\.\\d+))? MB").matcher(value);
					if (matcher.matches()) {
						value = matcher.group(1);
					}
					else {
						value = "0";
					}
				}

				if (key.equals("Pages")) {
					Matcher matcher = Pattern.compile("^(\\d+)$").matcher(value);
					if (matcher.matches()) {
						value = matcher.group(1);
					}
					else {
						value = "0";
					}
				}
			}
			result.put(key, value);
		}
		return result;
	}

	@Override
	String pageUrl() {
		return this.url;
	}
}
