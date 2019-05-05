package com.github.bogdanovmn.translator.core.text;

class Token {
	private final String value;

	private final static String EMPTY_LINE = "";
	final static String QUOTE = "\"";
	final static String DOT = ".";

	Token(String value) {
		this.value = value;
	}

	static Token emptyLine() {
		return new Token(EMPTY_LINE);
	}

	boolean isWord() {
		return value.matches("^[A-Za-z-]+$");
	}

	boolean isFirstCapital() {
		return isWord() && value.matches("^[A-Z][a-z]+");
	}

	boolean isCapital() {
		return value.matches("^[A-Z-]+$");
	}

	@Override
	public String toString() {
		return value;
	}
}
