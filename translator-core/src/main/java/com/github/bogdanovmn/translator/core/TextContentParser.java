package com.github.bogdanovmn.translator.core;

import java.io.IOException;

public interface TextContentParser {

	String text() throws IOException;

	String author() throws IOException;

	String title() throws IOException;

}
