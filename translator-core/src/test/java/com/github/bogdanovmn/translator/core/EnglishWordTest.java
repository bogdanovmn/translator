package com.github.bogdanovmn.translator.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class EnglishWordTest {
	@Test
	public void withPostfix() throws Exception {
		assertTrue(
			"Positive postfix",
			new EnglishWord("xxxxxxing").withAnyPostfix()
		);

		assertFalse(
			"Negative postfix",
			new EnglishWord("xxxxxx").withAnyPostfix()
		);
	}

	@Test
	public void withPrefix() throws Exception {
		assertTrue(
			"Positive prefix",
			new EnglishWord("superxxxxxx").withAnyPrefix()
		);

		assertFalse(
			"Negative prefix",
			new EnglishWord("xxxxxx").withAnyPrefix()
		);
	}

	@Test
	public void base() {
		assertEquals(
			"Prefix cut",
				"xxxxxx",
				new EnglishWord("superxxxxxx").base().toString()
		);

		assertEquals(
			"Postfix cut",
			"xxxxxx",
			new EnglishWord("xxxxxxies").base().toString()
		);

		assertEquals(
			"Prefix & postfix cut",
			"xxxxxx",
			new EnglishWord("superxxxxxxing").base().toString()
		);

		assertEquals(
			"Prefix & postfix cut (several items)",
			"xxxxxx",
			new EnglishWord("megasuperxxxxxxlessing").base().toString()
		);

		assertEquals(
			"No cut",
			"xxxxxx",
			new EnglishWord("xxxxxx").base().toString()
		);
	}

	@Test
	public void posibleForms() {
		assertTrue(
			new EnglishWord("xxxe").possibleForms().contains(new EnglishWord("xxxing"))
		);
	}
}