package com.github.bogdanovmn.translator.parser.pdf;

import com.github.bogdanovmn.translator.core.TextParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PdfTextContent implements TextParser {
	private final File source;

	public PdfTextContent(File source) {
		this.source = source;
	}

	@Override
	public String getText() throws IOException {
		return new PDFTextStripper()
			.getText(
				PDDocument.load(this.source)
			);
	}
}
