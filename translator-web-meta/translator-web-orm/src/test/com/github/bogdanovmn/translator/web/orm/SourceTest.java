package com.github.bogdanovmn.translator.web.orm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SourceTest {

	@Test
	public void getName() {
		assertEquals(
			"Rend Atlas Shrugged RuLit Net [txt]",
			new Source().setRawName("Rend_Atlas_Shrugged_RuLit_Net.txt").getName()
		);

		assertEquals(
			"Tolkien John. The Hobbit [royallib.com.txt.zip]",
			new Source().setRawName("Tolkien John. The Hobbit - royallib.com.txt.zip").getName()
		);

		assertEquals(
			"Crime [cvs]",
			new Source().setRawName("Crime.cvs").getName()
		);

		assertEquals(
			"Crime report",
			new Source().setRawName("Crime report").getName()
		);
	}
}