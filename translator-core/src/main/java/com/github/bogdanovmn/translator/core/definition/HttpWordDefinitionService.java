package com.github.bogdanovmn.translator.core.definition;

import com.github.bogdanovmn.httpclient.core.ExternalHttpService;
import com.github.bogdanovmn.httpclient.core.HttpClient;
import com.github.bogdanovmn.httpclient.core.HttpServiceException;
import com.github.bogdanovmn.httpclient.core.ResponseNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public abstract class HttpWordDefinitionService extends ExternalHttpService<Definition> implements WordDefinitionService {

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

		Definition definition = parsedServiceResponse(
			Objects.toString(htmlText, "")
		);

		String articleWord = definition.word();
		if (!articleWord.equals(word)) {
			if (Character.isUpperCase(articleWord.charAt(0))) {
				throw new ResponseNotFoundException(
					String.format("Proper name: '%s'", articleWord)
				);
			}
			throw new ResponseAnotherWordFormException(definition);
		}

		return definition.instances();
	}
}
