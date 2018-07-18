package com.github.bogdanovmn.translator.parser.pdf;

import com.github.bogdanovmn.translator.core.TextContentParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class PdfContent implements TextContentParser {
	private PDDocument document;

	public PdfContent(PDDocument document) {
		this.document = document;
	}

	public PdfContent(File source) throws IOException {
		this(
			PDDocument.load(source)
		);
	}

	public PdfContent(InputStream sourceStream) throws IOException {
		this(
			PDDocument.load(sourceStream)
		);
	}

	public PdfContent(byte[] sourceBytes) throws IOException {
		this(
			PDDocument.load(sourceBytes)
		);
	}

	@Override
	public String getText() throws IOException {
		return new PDFTextStripper().getText(document);
	}

	@Override
	public String getAuthor() throws IOException {
		return document.getDocumentInformation().getAuthor();
	}

	@Override
	public String getTitle() throws IOException {
		return document.getDocumentInformation().getTitle();
	}

	public void printMeta() {
		document.getDocumentInformation().getMetadataKeys().forEach(
			key -> System.out.println(
				String.format(
					"%s = %s",
						key,
						document.getDocumentInformation().getCustomMetadataValue(key)
				)
			)
		);
	}
}
