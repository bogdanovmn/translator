package com.github.bogdanovmn.translator.parser.common;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class DocumentContentTest {

	@Test
	public void shouldParsePdf() throws Exception {
		DocumentContent doc = DocumentContent.fromByteArray(
			Files.readAllBytes(
				Paths.get(
					getClass().getResource("/parser/sample.pdf").toURI()
				)
			)
		);

		doc.printMeta();

		assertTrue(doc.text().contains("Thank You For Listening"));

		assertEquals("Jens Rehsack", doc.author());

		assertEquals("Cross Compiling For Perl Hackers", doc.title());

		assertEquals("application/pdf", doc.contentType());
	}

	@Test
	public void shouldParsePlainText() throws Exception {
		DocumentContent doc = DocumentContent.fromByteArray(
			Files.readAllBytes(
				Paths.get(
					getClass().getResource("/parser/plain-text.txt").toURI()
				)
			)
		);

		doc.printMeta();

		assertTrue(doc.text().contains("test text"));

		assertNull(doc.author());

		assertNull(doc.title());

		assertEquals("text/plain; charset=ISO-8859-1", doc.contentType());

	}
}