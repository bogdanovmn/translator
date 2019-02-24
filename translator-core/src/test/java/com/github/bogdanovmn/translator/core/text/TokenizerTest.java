package com.github.bogdanovmn.translator.core.text;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TokenizerTest {

	@Test
	public void tokens() {
		Tokenizer tokenizer = Tokenizer.of(
			"“S-s-s-s-s,” hissed Gollum. “It must give us three guesseses, my preciouss, three guesseses.”"
		);

		tokenizer.tokens().forEach(System.out::println);
		assertEquals(
			Arrays.asList(
				"\"", "S-s-s-s-s", ",", "\"", "hissed", "Gollum", ".", "\"", "It", "must", "give", "us", "three", "guesseses",
					",", "my", "preciouss", ",", "three", "guesseses", ".", "\""
			),
			tokenizer.tokens()
		);
	}
}