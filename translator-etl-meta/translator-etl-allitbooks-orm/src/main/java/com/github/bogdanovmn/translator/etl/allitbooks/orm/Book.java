package com.github.bogdanovmn.translator.etl.allitbooks.orm;


import com.github.bogdanovmn.translator.core.text.CompressedText;
import com.github.bogdanovmn.translator.orm.core.BaseEntity;
import com.github.bogdanovmn.translator.parser.common.DocumentContent;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.binary.Hex;

import javax.persistence.*;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Date;

@Getter
@Setter

@Entity
@Table(
	name = "allitebook_data",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = "meta_id")
	}
)
public class Book extends BaseEntity {
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] data;
	private String fileHash;
	private int textSize;
	private Date created;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meta_id")
	private BookMeta meta;

	public static Book fromStream(InputStream dataStream) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		DigestInputStream digestInputStream = new DigestInputStream(dataStream, md);
		String text = DocumentContent.fromInputStream(digestInputStream).text();

		CompressedText compressedText = CompressedText.from(text);
		return new Book()
			.setCreated(new Date())
			.setTextSize(text.length())
			.setFileHash(
				Hex.encodeHexString(
					md.digest()
				)
			)
			.setData(compressedText.bytes());
	}

	@Override
	public String toString() {
		return String.format(
			"Book{data_size=%s, fileHash='%s', textSize=%d, created=%s}",
				data.length, fileHash, textSize, created
		);
	}
}
