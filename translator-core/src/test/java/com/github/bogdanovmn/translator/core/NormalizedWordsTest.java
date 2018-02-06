package com.github.bogdanovmn.translator.core;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class NormalizedWordsTest {
	@Test
	@Ignore
	public void get() throws Exception {
		NormalizedWords normalizedWords = new NormalizedWords(
			Arrays.asList(
//				"question", "questioning", "questions",
//				"talk", "talks", "talking", "talked",
//
//				"connect", "reconnect",
//				"system", "subsystem",
//
//				"someWord",
//
//				"abced", "abcing",

				"allocated", "unallocated", "allocate", "allocates", "allocation", "allocations"
			)
		);

		normalizedWords.printWordsWithForms();
		assertEquals(
			new HashSet<>(Arrays.asList("talk", "question", "someWord", "connect", "system", "allocate")),
			normalizedWords.get()
		);

		assertEquals(5, normalizedWords.get().size());
	}

}