package com.github.bogdanovmn.translator.core;

import com.github.bogdanovmn.translator.core.exception.TranslateServiceException;
import com.github.bogdanovmn.translator.core.exception.TranslateServiceUnavailableException;
import com.github.bogdanovmn.translator.core.exception.TranslateServiceUnknownWordException;
import com.github.bogdanovmn.translator.httpclient.HttpClient;

import java.io.IOException;

public abstract class HttpTranslateService implements TranslateService {
	private final HttpClient httpClient;

	public HttpTranslateService(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	protected abstract String parseServiceRawAnswer(String htmlText);

	@Override
	public final String translate(String phrase) throws TranslateServiceException {
		String htmlText;
		try {
			htmlText = httpClient.get(phrase);
		}
		catch (IOException e) {
			throw new TranslateServiceUnavailableException(e);
		}

		String translatedValue = this.parseServiceRawAnswer(htmlText);
		if (translatedValue != null) {
			if (translatedValue.isEmpty()) {
				throw new TranslateServiceUnavailableException("Empty result. Something wrong...");
			}
			if (translatedValue.equals(phrase)) {
				throw new TranslateServiceUnknownWordException(
					String.format(
						"No translate for '%s'", phrase
					)
				);
			}
		}
		return translatedValue;
	}

	@Override
	public void close() throws IOException {
		this.httpClient.close();
	}
}
