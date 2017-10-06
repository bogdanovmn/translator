package com.github.bogdanovmn.translator.parser.pdf;

import com.github.bogdanovmn.translator.core.TextContentParser;
import org.apache.pdfbox.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class PdfInputStreamContent implements TextContentParser {
	private final PdfContent pdfContent;

	public PdfInputStreamContent(InputStream sourceStream)
		throws IOException
	{
		Path tmpFilePath = Files.createTempFile("pdfContent.", ".pdf.tmp");
		tmpFilePath.toFile().deleteOnExit();

		Files.copy(sourceStream, tmpFilePath, StandardCopyOption.REPLACE_EXISTING);
		IOUtils.closeQuietly(sourceStream);

		this.pdfContent = new PdfContent(tmpFilePath.toFile());
	}

	@Override
	public String getText() throws IOException {
		return this.pdfContent.getText();
	}

	@Override
	public String getAuthor() throws IOException {
		return this.pdfContent.getAuthor();
	}

	@Override
	public String getTitle() throws IOException {
		return this.pdfContent.getTitle();
	}
}
