package com.github.bogdanovmn.translator.core.text;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.assertThat;

public class TokensTest {

	@Test
	public void tokens() {
		Tokens tokens = Tokens.of(
			"“S-s-s-s-s,” hissed Gollum. “It must give us three guesseses, my preciouss, three guesseses.”"
		);

		tokens.all().forEach(System.out::println);

		assertThat(
			tokens.all().stream().map(Object::toString).collect(Collectors.toList()),
			CoreMatchers.is(
				Arrays.asList(
					"\"", "S-s-s-s-s", ",", "\"", "hissed", "Gollum", ".", "\"", "It", "must", "give", "us", "three", "guesseses",
						",", "my", "preciouss", ",", "three", "guesseses", ".", "\""
				)
			)
		);
	}
}