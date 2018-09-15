package com.github.bogdanovmn.translator.etl.allitbooks;

import com.github.bogdanovmn.translator.web.orm.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "allitebook_data")
public class Book extends BaseEntity {
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] data;
	private String fileHash;
	private Integer textSize;
	private Date created;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "meta_id")
	private BookMeta meta;

	public byte[] getData() {
		return data;
	}

	public Book setData(byte[] data) {
		this.data = data;
		return this;
	}

	public String getFileHash() {
		return fileHash;
	}

	public Book setFileHash(String fileHash) {
		this.fileHash = fileHash;
		return this;
	}

	public Integer getTextSize() {
		return textSize;
	}

	public Book setTextSize(Integer textSize) {
		this.textSize = textSize;
		return this;
	}

	public Date getCreated() {
		return created;
	}

	public Book setCreated(Date created) {
		this.created = created;
		return this;
	}

	public BookMeta getMeta() {
		return meta;
	}

	public Book setMeta(BookMeta meta) {
		this.meta = meta;
		return this;
	}

	@Override
	public String toString() {
		return String.format(
			"Book{data_size=%s, fileHash='%s', textSize=%d, created=%s}",
				data.length, fileHash, textSize, created
		);
	}
}
