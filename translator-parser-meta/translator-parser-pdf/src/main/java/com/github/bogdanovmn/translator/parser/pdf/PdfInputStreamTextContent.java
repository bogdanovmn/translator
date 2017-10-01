package com.github.bogdanovmn.translator.parser.pdf;

import com.github.bogdanovmn.translator.core.TextParser;
import org.apache.pdfbox.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class PdfInputStreamTextContent implements TextParser {
	private final PdfTextContent pdfTextContent;

	public PdfInputStreamTextContent(InputStream sourceStream)
		throws IOException
	{
		Path tmpFilePath = Files.createTempFile("pdfTextContent.", ".pdf.tmp");
		tmpFilePath.toFile().deleteOnExit();

		Files.copy(sourceStream, tmpFilePath, StandardCopyOption.REPLACE_EXISTING);
		IOUtils.closeQuietly(sourceStream);

		this.pdfTextContent = new PdfTextContent(tmpFilePath.toFile());
	}

	@Override
	public String getText() throws IOException {
		return this.pdfTextContent.getText();
	}
}
