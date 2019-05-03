package com.github.bogdanovmn.translator.core.text;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class ProperNamesTest {

	@Test
	public void contains() {
		ProperNames properNames = ProperNames.fromWordTokens(
			Tokens.of(
				"The fires leaped up in black smokes. Ashes and cinders were in the eyes of the dwarves."
			)
		);

		assertFalse(
			properNames.contains("ashes")
		);
	}
}