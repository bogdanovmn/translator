package com.github.bogdanovmn.translator.core.text;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CompressedTextTest {

	@Test
	public void compress() throws IOException {
		CompressedText compressedText = CompressedText.from("Hello");
		assertEquals(
			"Hello",
			compressedText.decompress()
		);
	}
}