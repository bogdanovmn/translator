package com.github.bogdanovmn.translator.service.oxforddictionaries;

import com.github.bogdanovmn.httpclient.simple.SimpleHttpClient;
import com.github.bogdanovmn.translator.core.HttpWordDefinitionService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Set;

public class OxfordWordDefinition extends HttpWordDefinitionService {

	public static final String URL_PREFIX = "https://en.oxforddictionaries.com/definition/";

	public OxfordWordDefinition() {
		super(
			new SimpleHttpClient(),
			URL_PREFIX
		);
	}

	@Override
	protected Set<String> parsedServiceResponse(String htmlText) {
		Document doc = Jsoup.parse(htmlText);

		doc.select("section[class=gramb]").stream()
			.forEach(element -> {
				System.out.println(element.select("h3 span").text());
			});
		return null;
	}
}
