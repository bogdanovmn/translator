package com.github.bogdanovmn.translator.core;

import com.github.bogdanovmn.httpclient.core.HttpClient;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

public abstract class HttpTranslateService implements TranslateService {
	private final HttpClient httpClient;

	public HttpTranslateService(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	protected abstract Set<String> parseServiceRawAnswer(String htmlText) throws TranslateServiceException;

	@Override
	public final Set<String> translate(String phrase) throws TranslateServiceException {
		String htmlText;
		try {
			htmlText = httpClient.get(phrase);
		}
		catch (IOException e) {
			throw new TranslateServiceUnavailableException(e);
		}

		Set<String> translatedValue = this.parseServiceRawAnswer(
			Objects.toString(htmlText, "")
		);

		if (translatedValue == null || translatedValue.isEmpty()) {
			throw new TranslateServiceUnavailableException("Empty result. Something wrong...");
		}
		if (translatedValue.size() == 1 && translatedValue.contains(phrase)) {
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
