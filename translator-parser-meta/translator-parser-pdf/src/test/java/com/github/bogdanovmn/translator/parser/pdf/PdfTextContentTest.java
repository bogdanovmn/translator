package com.github.bogdanovmn.translator.parser.pdf;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class PdfTextContentTest {
	@Test
	public void getText() throws Exception {
		PdfTextContent pdf = new PdfTextContent(
			new File(
				getClass().getResource("/sample.pdf").getFile()
			)
		);
		assertTrue(pdf.getText().contains("Thank You For Listening"));
	}

}