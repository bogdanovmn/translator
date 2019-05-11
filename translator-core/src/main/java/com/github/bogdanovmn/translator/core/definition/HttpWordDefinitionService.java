package com.github.bogdanovmn.translator.core.definition;

import com.github.bogdanovmn.httpclient.core.HttpClient;
import com.github.bogdanovmn.httpclient.core.HttpService;
import com.github.bogdanovmn.httpclient.core.HttpServiceException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public abstract class HttpWordDefinitionService extends HttpService<List<DefinitionInstance>> implements WordDefinitionService {

	public HttpWordDefinitionService(HttpClient httpClient, String urlPrefix) {
		super(httpClient, urlPrefix);
	}

	@Override
	public final List<DefinitionInstance> definitions(String word) throws HttpServiceException {
		String htmlText;
		try {
			htmlText = httpClient.get(urlPrefix + word);
		}
		catch (IOException e) {
			throw new HttpServiceException(e);
		}

		return parsedServiceResponse(
			Objects.toString(htmlText, ""),
			word
		);
	}
}
