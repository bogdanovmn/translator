package com.github.bogdanovmn.translator.core;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public class Timer {

	public static <T> T messure(String description, Supplier<T> supplier) {
		long start = System.currentTimeMillis();
		T result = supplier.get();
		LOG.info(
			String.format(
				"%s took %.3f sec",
					description, (System.currentTimeMillis() - start) / 1000.0
			)
		);
		return result;
	}
}
