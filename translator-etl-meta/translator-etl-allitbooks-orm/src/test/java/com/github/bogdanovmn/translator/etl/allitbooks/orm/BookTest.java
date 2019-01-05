package com.github.bogdanovmn.translator.etl.allitbooks.orm;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class BookTest {

	@Test
	public void fromStream() throws Exception {
		Book book = Book.fromStream(
			Files.newInputStream(
				Paths.get(
					getClass().getResource("/test.pdf").toURI()
				)
			)
		);

		assertEquals(
			"094605b4249698bcf866439f6d88b227",
			book.getFileHash()
		);

		assertEquals(
			3651,
			book.getTextSize()
		);
	}
}