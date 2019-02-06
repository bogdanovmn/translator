package com.github.bogdanovmn.translator.core;

public class BigString {
	private final StringBuilder stringBuilder;

	private BigString(final StringBuilder stringBuilder) {
		this.stringBuilder = stringBuilder;
	}

	public BigString() {
		this(new StringBuilder());
	}

	public BigString add(String msgFormat, Object... args) {
		stringBuilder.append(
			String.format(
				msgFormat, args
			)
		);
		return this;
	}

	public BigString addLine(String msgFormat, Object... args) {
		add(msgFormat + "%n", args);
		return this;
	}

	@Override
	public String toString() {
		return this.stringBuilder.toString();
	}
}
