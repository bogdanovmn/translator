package com.github.bogdanovmn.translator.core.translate;

import org.junit.Test;

import static org.junit.Assert.*;

public class IrregularVerbCollectionTest {

	@Test
	public void get() {
		IrregularVerbCollection verbs = IrregularVerbCollection.create();

		assertEquals(
			"be",
			verbs.get("was").get().baseForm()
		);
	}
}
