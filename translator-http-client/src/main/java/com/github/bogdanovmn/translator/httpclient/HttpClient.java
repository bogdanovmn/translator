package com.github.bogdanovmn.translator.httpclient;

import java.io.Closeable;
import java.io.IOException;

public interface HttpClient extends Closeable {
	String get(String path) throws IOException;
}
