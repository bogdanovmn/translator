package com.github.bogdanovmn.translator.core;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class EnglishTextTest {
	@Test
	public void wordWrap() {
		EnglishText text = new EnglishText(
			"bla groovy-lang bla bla execu-\ntion " +
				"proce‐ dure"
		);

		text.printStatistic();

		assertEquals("execu forms freq"      , 0, text.getWordFormsFrequance("execu"));
		assertEquals("tion forms freq"       , 0, text.getWordFormsFrequance("tion"));
		assertEquals("execution forms freq"  , 1, text.getWordFormsFrequance("execution"));
		assertEquals("groovy-lang forms freq", 0, text.getWordFormsFrequance("groovy-lang"));
		assertEquals("groovy forms freq"     , 1, text.getWordFormsFrequance("groovy"));
		assertEquals("lang forms freq"       , 1, text.getWordFormsFrequance("lang"));
		assertEquals("proce forms freq"      , 0, text.getWordFormsFrequance("proce"));
		assertEquals("procedure forms freq"  , 1, text.getWordFormsFrequance("procedure"));
	}
	@Test
	public void getWordFormsFrequance() throws Exception {
		EnglishText text = new EnglishText(
			"question questioning questions " +
			"talk talks talking talked " +
			"connect reconnect " +
			"system subsystem " +
			"someword"
		);

		text.printStatistic();

		assertEquals("question forms freq", 3, text.getWordFormsFrequance("question"));
		assertEquals("talk forms freq"    , 4, text.getWordFormsFrequance("talk"));
		assertEquals("someword forms freq", 1, text.getWordFormsFrequance("someword"));
		assertEquals("blabla forms freq"  , 0, text.getWordFormsFrequance("blabla"));
	}

	@Test
	public void getWordFrequance() throws Exception {
		EnglishText text = new EnglishText(
			"hello, world! hello, man! " +
			"size_t hello_World  " +
			"a aa " +
			"goood iii " +
			"bsd sdk cxxldflags " +
			"executorDriver ExecutorClassName "
		);

		text.printStatistic();

		assertEquals("hello freq"  , 3, text.getWordFrequance("hello"));
		assertEquals("world freq"  , 2, text.getWordFrequance("world"));
		assertEquals("size freq"   , 1, text.getWordFrequance("size"));
		assertEquals("t freq"      , 0, text.getWordFrequance("t"));
		assertEquals("a freq"      , 0, text.getWordFrequance("a"));
		assertEquals("aa freq"     , 0, text.getWordFrequance("aa"));
		assertEquals("sdk freq"    , 0, text.getWordFrequance("bsd"));
		assertEquals("goood freq"  , 0, text.getWordFrequance("goood"));
		assertEquals("iii freq"    , 0, text.getWordFrequance("iii"));
		assertEquals("cxxldflag freq", 0, text.getWordFrequance("cxxldflag"));
		assertEquals("executor freq" , 2, text.getWordFrequance("executor"));
		assertEquals("driver freq"   , 1, text.getWordFrequance("driver"));
		assertEquals("class freq"    , 1, text.getWordFrequance("class"));
		assertEquals("name freq"     , 1, text.getWordFrequance("name"));
	}

	@Test
	public void words() throws Exception {
		EnglishText text = new EnglishText("hello, world! test2role руссиш");

		assertEquals(4, text.words().size());
	}

	@Test
	public void iesPostfix() throws Exception {
		EnglishText text = new EnglishText("binary binaries");

		text.printStatistic();

		assertEquals("binary forms freq", 2, text.getWordFormsFrequance("binary"));

		assertEquals(2, text.words().size());
	}

	@Test
	public void versionWordForms() {
		EnglishText text = new EnglishText(
			"verified verifies " +
			"verify --> [ subversion, versions, version ]"
		);

		text.printStatistic();

		assertEquals("version forms freq", 3, text.getWordFormsFrequance("version"));
	}

	@Test @Ignore
	public void serializeWordForms() {
		EnglishText text = new EnglishText(
			"serialize  --> [serialization] " +
			"serialized --> [serializable, serializing]"
		);

		text.printStatistic();

		assertEquals("serialize forms freq", 3, text.getWordFormsFrequance("serialize"));
	}

	@Test
	public void transformWordForms() {
		EnglishText text = new EnglishText(
			"transform --> [transforming] " +
				"transformation --> [transformations]"
		);

		text.printStatistic();

		assertEquals("transform forms freq", 4, text.getWordFormsFrequance("transform"));
	}

	@Test
	public void smallBaseWordForms() {
		EnglishText text = new EnglishText(
			"var  --> [varies] "
		);

		text.printStatistic();

		assertEquals("var forms freq", 0, text.getWordFormsFrequance("var"));
	}
}