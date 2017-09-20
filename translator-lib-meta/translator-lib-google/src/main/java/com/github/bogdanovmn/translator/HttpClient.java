package com.github.bogdanovmn.translator;

import java.io.IOException;

public interface HttpClient {
	String get(String path) throws IOException;
}
