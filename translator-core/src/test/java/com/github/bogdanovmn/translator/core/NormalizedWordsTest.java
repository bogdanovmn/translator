package com.github.bogdanovmn.translator.core;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

public class NormalizedWordsTest {
	@Test
	public void get() throws Exception {
		NormalizedWords normalizedWords = new NormalizedWords(
			Arrays.asList(
				"question", "questioning", "questions",
				"talk", "talks", "talking",
				"someWord"
			)
		);

		normalizedWords.printWordsWithForms();
		assertEquals(
			new HashSet<>(Arrays.asList("talk", "question")),
			normalizedWords.get()
		);
	}

}