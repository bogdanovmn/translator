package com.github.bogdanovmn.translator.parser.pdf;

import com.github.bogdanovmn.translator.core.TextContentParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PdfContent implements TextContentParser {
	private final File source;
	private boolean isPrepared = false;
	private PDDocument document;

	public PdfContent(File source) {
		this.source = source;
	}

	private void prepare() throws IOException {
		if (!this.isPrepared) {
			try {
				this.document = PDDocument.load(this.source);
			}
			finally {
				this.isPrepared = true;
			}
		}
	}
	@Override
	public String getText() throws IOException {
		this.prepare();
		return new PDFTextStripper().getText(this.document);
	}

	@Override
	public String getAuthor() throws IOException {
		this.prepare();
		return this.document.getDocumentInformation().getAuthor();
	}

	@Override
	public String getTitle() throws IOException {
		this.prepare();
		return this.document.getDocumentInformation().getTitle();
	}
}
