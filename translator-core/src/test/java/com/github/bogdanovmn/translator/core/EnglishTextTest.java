package com.github.bogdanovmn.translator.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class EnglishTextTest {
	@Test
	public void getWordFrequance() throws Exception {
		EnglishText text = new EnglishText(
			"hello, world! hello, man! " +
			"size_t hello_World  " +
			"a aa " +
			"goood iii " +
			"bsd sdk cxxldflags"
		);

		text.printStatistic();

		assertEquals("hello freq"  , 3, text.getWordFrequance("hello"));
		assertEquals("world freq"  , 2, text.getWordFrequance("world"));
		assertEquals("size freq"   , 1, text.getWordFrequance("size"));
		assertEquals("t freq"      , 0, text.getWordFrequance("t"));
		assertEquals("a freq"      , 0, text.getWordFrequance("a"));
		assertEquals("aa freq"     , 0, text.getWordFrequance("aa"));
//		assertEquals("aaa freq"    , 0, text.getWordFrequance("aaa"));
		assertEquals("sdk freq"    , 0, text.getWordFrequance("bsd"));
		assertEquals("goood freq"  , 0, text.getWordFrequance("goood"));
		assertEquals("iii freq"    , 0, text.getWordFrequance("iii"));
		assertEquals("cxxldflag freq", 0, text.getWordFrequance("cxxldflag"));
	}

	@Test
	public void words() throws Exception {
		EnglishText text = new EnglishText("hello, world! test2role руссиш");

		assertEquals(4, text.words().size());
	}

}