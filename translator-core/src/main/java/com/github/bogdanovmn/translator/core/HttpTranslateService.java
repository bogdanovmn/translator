package com.github.bogdanovmn.translator.core;

import com.github.bogdanovmn.translator.core.exception.TranslateServiceException;
import com.github.bogdanovmn.translator.core.exception.TranslateServiceUnavailableException;
import com.github.bogdanovmn.translator.core.exception.TranslateServiceUnknownWordException;
import com.github.bogdanovmn.translator.httpclient.HttpClient;

import java.io.IOException;
import java.util.Objects;

public abstract class HttpTranslateService implements TranslateService {
	private final HttpClient httpClient;

	public HttpTranslateService(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	protected abstract String parseServiceRawAnswer(String htmlText) throws TranslateServiceException;

	@Override
	public final String translate(String phrase) throws TranslateServiceException {
		String htmlText;
		try {
			htmlText = httpClient.get(phrase);
		}
		catch (IOException e) {
			throw new TranslateServiceUnavailableException(e);
		}

		String translatedValue = this.parseServiceRawAnswer(
			Objects.toString(htmlText, "")
		);

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

		return translatedValue;
	}

	@Override
	public void close() throws IOException {
		this.httpClient.close();
	}
}
