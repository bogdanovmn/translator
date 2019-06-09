package com.github.bogdanovmn.translator.core.text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

public class CompressedText {
	private final byte[] data;

	public CompressedText(byte[] data) {
		this.data = data;
	}

	public static CompressedText from(String text) throws IOException {
		ByteArrayOutputStream compressedStream = new ByteArrayOutputStream();
		try (OutputStream out = new DeflaterOutputStream(compressedStream)) {
			out.write(text.getBytes());
		}
		return new CompressedText(compressedStream.toByteArray());
	}

	public String decompress() throws IOException {
		ByteArrayOutputStream decompressedStream = new ByteArrayOutputStream();
		try (OutputStream out = new InflaterOutputStream(decompressedStream)) {
			out.write(data);
		}
		return new String(decompressedStream.toByteArray(), StandardCharsets.UTF_8);
	}

	public byte[] bytes() {
		return data;
	}
}
