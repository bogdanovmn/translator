package com.github.bogdanovmn.translator.parser.common;

import com.github.bogdanovmn.translator.core.TextContentParser;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class DocumentContent implements TextContentParser {
	private final Metadata metadata;
	private final BodyContentHandler bodyContentHandler;

	private DocumentContent(Metadata metadata, BodyContentHandler bodyContentHandler) {
		this.metadata = metadata;
		this.bodyContentHandler = bodyContentHandler;
	}

	public static DocumentContent fromByteArray(byte[] fileData) throws IOException {
		AutoDetectParser parser;
		try {
			TikaConfig config = new TikaConfig(
					Paths.get(
						DocumentContent.class.getClassLoader().getResource("tika-config.xml").toURI()
					)
			);
			parser = new AutoDetectParser(
				config
			);
		}
		catch (Exception e) {
			throw new IOException("Tika config error", e);
		}

		BodyContentHandler handler = new BodyContentHandler(-1);
		Metadata metadata = new Metadata();
		try (InputStream stream = new ByteArrayInputStream(fileData)) {
			parser.parse(stream, handler, metadata);
		}
		catch (Exception e) {
			throw new IOException("Parse file error", e);
		}

		return new DocumentContent(metadata, handler);
	}

	public static DocumentContent fromFile(File file) throws IOException {
		return DocumentContent.fromByteArray(
			Files.readAllBytes(
				Paths.get(file.toURI())
			)
		);
	}

	@Override
	public String text() {
		return bodyContentHandler.toString();
	}

	@Override
	public String author() throws IOException {
		return metadata.get("creator");
	}

	@Override
	public String title() throws IOException {
		return metadata.get("title");
	}

	public String contentType() {
		return metadata.get("Content-Type");
	}

	public void printMeta() {
		String[] keys = metadata.names();
		Stream.of(keys).forEach(
			key -> System.out.println(
				String.format(
					"%s = %s",
						key,
						metadata.get(key)
				)
			)
		);
	}
}
