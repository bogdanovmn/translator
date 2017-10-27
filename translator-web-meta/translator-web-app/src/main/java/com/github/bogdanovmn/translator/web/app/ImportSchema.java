package com.github.bogdanovmn.translator.web.app;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "translatorExport")
@XmlAccessorType(XmlAccessType.FIELD)
class ImportSchema {
	@XmlElementWrapper
	@XmlElement(name = "source")
	private List<ExSource> sources;

	@XmlElementWrapper
	@XmlElement(name = "translateProvider")
	private List<ExTranslateProvider> translateProviders;

	@XmlElementWrapper
	@XmlElement(name = "word")
	private List<ExWord> words;

	@XmlElementWrapper
	@XmlElement(name = "translate")
	private List<ExTranslate> translates;

	@XmlElementWrapper
	@XmlElement(name = "link")
	private List<ExWordSource> wordSources;

	@XmlElementWrapper
	@XmlElement(name = "user")
	private List<ImportSchema.ExUser> users = new ArrayList<>();

	ImportSchema setSources(List<ExSource> sources) {
		this.sources = sources;
		return this;
	}

	ImportSchema setTranslateProviders(List<ExTranslateProvider> translateProviders) {
		this.translateProviders = translateProviders;
		return this;
	}

	ImportSchema setWords(List<ExWord> words) {
		this.words = words;
		return this;
	}

	ImportSchema setTranslates(List<ExTranslate> translates) {
		this.translates = translates;
		return this;
	}

	ImportSchema setWordSources(List<ExWordSource> wordSources) {
		this.wordSources = wordSources;
		return this;
	}

	ImportSchema setUsers(List<ExUser> users) {
		this.users = users;
		return this;
	}

	List<ExSource> getSources() {
		return sources;
	}

	List<ExTranslateProvider> getTranslateProviders() {
		return translateProviders;
	}

	List<ExWord> getWords() {
		return words;
	}

	List<ExTranslate> getTranslates() {
		return translates;
	}

	List<ExWordSource> getWordSources() {
		return wordSources;
	}

	List<ExUser> getUsers() {
		return users;
	}

	static class ExUser {
		@XmlAttribute
		private String email;
		@XmlList
		private List<Integer> rememberedWords;

		String getEmail() {
			return email;
		}

		ExUser setEmail(String email) {
			this.email = email;
			return this;
		}

		List<Integer> getRememberedWords() {
			return rememberedWords;
		}

		ExUser setRememberedWords(List<Integer> rememberedWords) {
			this.rememberedWords = rememberedWords;
			return this;
		}
	}

	static class ExSource {
		@XmlAttribute(name = "ref")
		private int id;
		@XmlAttribute
		private String rawName;
		@XmlAttribute
		private String contentHash;
		@XmlAttribute
		private String type;
		@XmlAttribute
		private int wordsCount;

		public int getId() {
			return id;
		}

		String getRawName() {
			return rawName;
		}

		public String getContentHash() {
			return contentHash;
		}

		public String getType() {
			return type;
		}

		public int getWordsCount() {
			return wordsCount;
		}
	}

	static  class ExTranslateProvider {
		@XmlAttribute(name = "ref")
		private int id;
		private String name;

	}

	static class ExWord {
		@XmlAttribute(name = "ref")
		private int id;
		@XmlAttribute
		private String name;

	}

	static class ExTranslate {
		@XmlAttribute(name = "wid")
		private int wordId;
		@XmlAttribute(name = "pid")
		private int providerId;
		@XmlAttribute
		private String value;

		public int getWordId() {
			return wordId;
		}

		int getProviderId() {
			return providerId;
		}

		public String getValue() {
			return value;
		}
	}

	static class ExWordSource {
		@XmlAttribute(name = "wid")
		private int wordId;
		@XmlAttribute(name = "sid")
		private int sourceId;
		@XmlAttribute
		private int count;
	}
}

