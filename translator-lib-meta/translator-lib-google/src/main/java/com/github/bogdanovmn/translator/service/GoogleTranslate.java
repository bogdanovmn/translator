package com.github.bogdanovmn.translator.service;

import com.github.bogdanovmn.translator.core.HttpTranslateService;
import com.github.bogdanovmn.translator.httpclient.PhantomJsHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class GoogleTranslate extends HttpTranslateService {

	public GoogleTranslate() {
		super(
			new PhantomJsHttpClient("https://translate.google.ru/?ie=UTF-8#en/ru/")
		);
	}

	@Override
	protected String parseServiceRawAnswer(String htmlText) {
		Element resultBox = Jsoup.parse(htmlText)
			.select("span[id=result_box]")
			.first();

		return resultBox != null
			? resultBox.text()
			: null;
	}
}
