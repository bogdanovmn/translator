package com.github.bogdanovmn.translator;

import com.github.bogdanovmn.translator.core.TranslateService;
import com.github.bogdanovmn.translator.core.TranslateServiceException;
import com.github.bogdanovmn.translator.core.TranslateServiceUnavailableException;
import com.github.bogdanovmn.translator.core.TranslateServiceUnknownWordException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class GoogleTranslate implements TranslateService {
	private HttpClient httpClient = new SimpleHttpClient("https://translate.google.ru/?ie=UTF-8#en/ru/");

	@Override
	public String translate(String phrase) throws TranslateServiceException {
		String htmlText;
		try {
			htmlText = httpClient.get(phrase);
		}
		catch (IOException e) {
			throw new TranslateServiceUnavailableException(e);
		}

		Element resultBox = Jsoup.parse(htmlText)
			.select("span[id=result_box]")
			.first();

		String result = "";
		if (resultBox != null) {
			String resultBoxText = resultBox.text();
			if (resultBoxText.isEmpty()) {
				throw new TranslateServiceUnavailableException("Empty result. Something wrong...");
			}
			if (resultBoxText.equals(phrase)) {
				throw new TranslateServiceUnknownWordException(
					String.format(
						"No translate for '%s'", phrase
					)
				);
			}
			result = resultBoxText;
		}
		return result;
	}
}
