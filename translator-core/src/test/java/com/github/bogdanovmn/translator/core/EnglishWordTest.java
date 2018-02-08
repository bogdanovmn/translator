package com.github.bogdanovmn.translator.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class EnglishWordTest {
	@Test
	public void withPostfix() throws Exception {
		assertTrue(
			"Positive postfix",
			new EnglishWord("xxxing").withPostfix()
		);

		assertFalse(
			"Negative postfix",
			new EnglishWord("xxx").withPostfix()
		);
	}

	@Test
	public void withPrefix() throws Exception {
		assertTrue(
			"Positive prefix",
			new EnglishWord("superxxx").withPrefix()
		);

		assertFalse(
			"Negative prefix",
			new EnglishWord("xxx").withPrefix()
		);
	}

	@Test
	public void base() {
		assertEquals(
			"Prefix cut",
				"xxx",
				new EnglishWord("superxxx").base().toString()
		);

		assertEquals(
			"Postfix cut",
			"xxx",
			new EnglishWord("xxxing").base().toString()
		);

		assertEquals(
			"Prefix & postfix cut",
			"xxx",
			new EnglishWord("superxxxing").base().toString()
		);

		assertEquals(
			"Prefix & postfix cut (several items)",
			"xxx",
			new EnglishWord("megasuperxxxlessing").base().toString()
		);

		assertEquals(
			"No cut",
			"xxx",
			new EnglishWord("xxx").base().toString()
		);
	}

	@Test
	public void posibleForms() {
		assertTrue(
			new EnglishWord("xxxe").posibleForms().contains(new EnglishWord("xxxing"))
		);
	}
}