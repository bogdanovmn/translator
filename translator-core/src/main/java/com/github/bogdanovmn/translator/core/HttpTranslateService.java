package com.github.bogdanovmn.translator.core;

import com.github.bogdanovmn.httpclient.core.HttpClient;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

public abstract class HttpTranslateService extends HttpService<Set<String>> implements TranslateService {

	public HttpTranslateService(HttpClient httpClient, String urlPrefix) {
		super(httpClient, urlPrefix);
	}

	@Override
	public final Set<String> translate(String phrase) throws TranslateServiceException {
		String htmlText;
		try {
			htmlText = httpClient.get(urlPrefix + phrase);
		}
		catch (IOException e) {
			throw new TranslateServiceException(e);
		}

		Set<String> translatedValue;
		try {
			translatedValue = parsedServiceResponse(
				Objects.toString(htmlText, "")
			);
		} catch (ParseResponseException e) {
			throw new TranslateServiceException(e);
		}

		if (translatedValue == null || translatedValue.isEmpty()) {
			throw new TranslateServiceException("Empty result. Something wrong...");
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
}
