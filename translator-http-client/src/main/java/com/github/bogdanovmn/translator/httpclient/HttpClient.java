package com.github.bogdanovmn.translator.httpclient;

import java.io.IOException;

public interface HttpClient {
	String get(String path) throws IOException;
}
