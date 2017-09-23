package com.github.bogdanovmn.translator.service.yandex;

import com.github.bogdanovmn.translator.core.HttpTranslateService;
import com.github.bogdanovmn.translator.httpclient.HttpClient;
import com.github.bogdanovmn.translator.httpclient.SimpleHttpClient;

public class YandexTranslate extends HttpTranslateService {
	public YandexTranslate() {
		super(
			new SimpleHttpClient(
				"https://translate.yandex.net/api/v1/tr.json/translate?id=0ba9ce9a.59c6161b.f4e73d1c-0-0&srv=tr-text&lang=en-ru&reason=paste&exp=1&text="
			)
		);
	}

	@Override
	protected String parseServiceRawAnswer(String htmlText) {
		return null;
	}
}
