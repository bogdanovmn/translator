package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.web.orm.*;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "translatorExport")
public class ExportSchema {
	@XmlElementWrapper
	@XmlElement(name = "source")
	private List<Source> sources;

	@XmlElementWrapper
	@XmlElement(name = "translateProvider")
	private List<TranslateProvider> translateProviders;

	@XmlElementWrapper
	@XmlElement(name = "word")
	private List<Word> words;

	@XmlElementWrapper
	@XmlElement(name = "translate")
	private List<Translate> translates;

	@XmlElementWrapper
	@XmlElement(name = "link")
	private List<WordSource> wordSources;

	@XmlElementWrapper(name = "users")
	@XmlElement(name = "user")
	private List<ExportSchema.User> users;

	public ExportSchema setSources(List<Source> sources) {
		this.sources = sources;
		return this;
	}

	public ExportSchema setTranslateProviders(List<TranslateProvider> translateProviders) {
		this.translateProviders = translateProviders;
		return this;
	}

	public ExportSchema setWords(List<Word> words) {
		this.words = words;
		return this;
	}

	public ExportSchema setTranslates(List<Translate> translates) {
		this.translates = translates;
		return this;
	}

	public ExportSchema setWordSources(List<WordSource> wordSources) {
		this.wordSources = wordSources;
		return this;
	}

	public ExportSchema setUsers(List<UserRememberedWord> users) {
		this.users = users;
		return this;
	}

	private class User {
		@XmlAttribute
		private String email;
		@XmlElementWrapper
		@XmlElement(name = "word")
		private List<Integer> rememberedWords;
	}
}
