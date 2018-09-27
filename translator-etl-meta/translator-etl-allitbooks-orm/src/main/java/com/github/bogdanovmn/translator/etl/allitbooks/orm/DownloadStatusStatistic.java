package com.github.bogdanovmn.translator.etl.allitbooks.orm;

public class DownloadStatusStatistic {
	private final DownloadStatus status;
	private final int count;

	public DownloadStatusStatistic(String status, int count) {
		this.status = DownloadStatus.valueOf(status);
		this.count = count;
	}
}
