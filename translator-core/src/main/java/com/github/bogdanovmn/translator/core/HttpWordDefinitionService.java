package com.github.bogdanovmn.translator.core;

import com.github.bogdanovmn.httpclient.core.HttpClient;

import java.io.IOException;
import java.util.Set;

public abstract class HttpWordDefinitionService extends HttpService<Set<String>> implements WordDefinitionService {

	public HttpWordDefinitionService(HttpClient httpClient, String urlPrefix) {
		super(httpClient, urlPrefix);
	}

	@Override
	public final Set<String> definition(String phrase) throws WordDefinitionServiceException {
		String htmlText;
		try {
			htmlText = httpClient.get(urlPrefix + phrase);
		}
		catch (IOException e) {
			throw new WordDefinitionServiceException(e);
		}

		return null;
	}
}
