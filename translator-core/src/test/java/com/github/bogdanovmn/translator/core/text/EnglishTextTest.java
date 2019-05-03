package com.github.bogdanovmn.translator.core.text;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class EnglishTextTest {
	@Test
	public void wordWrap() {
		EnglishText text = EnglishText.fromText(
			"bla groovy-lang bla bla execu-\ntion " +
				"proce‐ dure " +
				"agile‐related JSON-\nformatted JSON-formatted " +
				"RESTAU- RANT"
		);

		System.out.println(text.statistic());

		assertEquals("execu forms freq"      , 0, text.wordFrequency("execu"));
		assertEquals("tion forms freq"       , 0, text.wordFrequency("tion"));
		assertEquals("execution forms freq"  , 1, text.wordFrequency("execution"));
		assertEquals("groovy-lang forms freq", 0, text.wordFrequency("groovy-lang"));
		assertEquals("groovy forms freq"     , 1, text.wordFrequency("groovy"));
		assertEquals("lang forms freq"       , 1, text.wordFrequency("lang"));
		assertEquals("proce forms freq"      , 0, text.wordFrequency("proce"));
		assertEquals("procedure forms freq"  , 1, text.wordFrequency("procedure"));
		assertEquals("agile forms freq"      , 1, text.wordFrequency("agile"));
		assertEquals("related forms freq"    , 1, text.wordFrequency("related"));
		assertEquals("formatted forms freq"  , 2, text.wordFrequency("formatted"));
		assertEquals("restaurant forms freq" , 1, text.wordFrequency("restaurant"));
	}
	@Test
	public void getWordFormsFrequency() throws Exception {
		EnglishText text = EnglishText.fromText(
			"question questioning questions " +
			"talk talks talking talked " +
			"connect reconnect " +
			"system subsystem " +
			"someword"
		);

		System.out.println(text.statistic());

		assertEquals("question forms freq", 3, text.wordFrequency("question"));
		assertEquals("talk forms freq"    , 4, text.wordFrequency("talk"));
		assertEquals("someword forms freq", 1, text.wordFrequency("someword"));
		assertEquals("blabla forms freq"  , 0, text.wordFrequency("blabla"));
	}

	@Test
	public void getWordFrequency() throws Exception {
		EnglishText text = EnglishText.fromText(
			"&#230;sophagus " +
			"hello, world! hello, man! " +
			"size_t hello_World  " +
			"a aa " +
			"goood iii " +
			"bsd sdk cxxldflags " +
			"executorDriver ExecutorClassName " +
			"InfoQ /UCRandBGInfo " +
			"UTF-8 bytes \"42D5GrxOQFebf83DYgNl-g\" "
		);

		System.out.println(text.statistic());

		assertEquals("hello freq"  , 3, text.wordFrequency("hello"));
		assertEquals("world freq"  , 2, text.wordFrequency("world"));
		assertEquals("size freq"   , 1, text.wordFrequency("size"));
		assertEquals("t freq"      , 0, text.wordFrequency("t"));
		assertEquals("a freq"      , 0, text.wordFrequency("a"));
		assertEquals("aa freq"     , 0, text.wordFrequency("aa"));
		assertEquals("sdk freq"    , 0, text.wordFrequency("bsd"));
		assertEquals("goood freq"  , 0, text.wordFrequency("goood"));
		assertEquals("iii freq"    , 0, text.wordFrequency("iii"));
		assertEquals("cxxldflag freq", 0, text.wordFrequency("cxxldflag"));
		assertEquals("executor freq" , 2, text.wordFrequency("executor"));
		assertEquals("driver freq"   , 1, text.wordFrequency("driver"));
		assertEquals("class freq"    , 1, text.wordFrequency("class"));
		assertEquals("name freq"     , 1, text.wordFrequency("name"));
		assertEquals("info freq"     , 2, text.wordFrequency("info"));
		assertEquals("bginfo freq"   , 0, text.wordFrequency("bginfo"));
		assertEquals("bytes freq"    , 1, text.wordFrequency("bytes"));
		assertEquals("febf freq"     , 0, text.wordFrequency("febf"));
		assertEquals("sophagus freq" , 1, text.wordFrequency("sophagus"));
	}

	@Test
	public void words() throws Exception {
		EnglishText text = EnglishText.fromText("hello, world! test2role руссиш");

		System.out.println(text.statistic());

		assertEquals(2, text.uniqueWords().size());
	}

	@Test
	public void iesPostfix() throws Exception {
		EnglishText text = EnglishText.fromText("binary binaries");

		System.out.println(text.statistic());

		assertEquals("binary forms freq", 2, text.wordFrequency("binary"));

		assertEquals(2, text.uniqueWords().size());
	}

	@Test
	public void versionWordForms() {
		EnglishText text = EnglishText.fromText(
			"verified verifies " +
			"verify --> [ subversion, versions, version ]"
		);

		System.out.println(text.statistic());

		assertEquals("version forms freq", 3, text.wordFrequency("version"));
	}

	@Test @Ignore
	public void serializeWordForms() {
		EnglishText text = EnglishText.fromText(
			"serialize  --> [serialization] " +
			"serialized --> [serializable, serializing]"
		);

		System.out.println(text.statistic());

		assertEquals("serialize forms freq", 3, text.wordFrequency("serialize"));
	}

	@Test
	public void transformWordForms() {
		EnglishText text = EnglishText.fromText(
			"transform --> [transforming] " +
				"transformation --> [transformations]"
		);

		System.out.println(text.statistic());

		assertEquals("transform forms freq", 4, text.wordFrequency("transform"));
	}

	@Test
	public void smallBaseWordForms() {
		EnglishText text = EnglishText.fromText(
			"var  --> [varies] "
		);

		System.out.println(text.statistic());

		assertEquals("var forms freq", 0, text.wordFrequency("var"));
	}
}