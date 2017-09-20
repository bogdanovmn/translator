package com.github.bogdanovmn.translator.httpclient;

import org.apache.http.client.fluent.Request;

import java.io.IOException;

public class SimpleHttpClient implements HttpClient {
	private final String urlPrefix;

	public SimpleHttpClient(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}

	@Override
	public String get(String url) throws IOException {
		return Request.Get(urlPrefix + url)
			.connectTimeout(10000)
			.execute()
				.returnContent().asString();
	}
}
