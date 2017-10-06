package com.github.bogdanovmn.translator.core;

import java.io.IOException;

public interface TextContentParser {

	String getText() throws IOException;

	String getAuthor() throws IOException;

	String getTitle() throws IOException;

}
