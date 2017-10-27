package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.web.orm.Source;
import com.github.bogdanovmn.translator.web.orm.SourceType;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "translatorExport")
@XmlAccessorType(XmlAccessType.FIELD)
class ImportSchema {
	@XmlElementWrapper
	@XmlElement(name = "source")
	private List<ImportSource> sources;

	@XmlElementWrapper
	@XmlElement(name = "translateProvider")
	private List<ImportTranslateProvider> translateProviders;

	@XmlElementWrapper
	@XmlElement(name = "word")
	private List<ImportWord> words;

	@XmlElementWrapper
	@XmlElement(name = "translate")
	private List<ImportTranslate> translates;

	@XmlElementWrapper
	@XmlElement(name = "link")
	private List<ExWordSource> wordSources;

	@XmlElementWrapper
	@XmlElement(name = "user")
	private List<ImportUser> users = new ArrayList<>();
//
//	ImportSchema setSources(List<ImportSource> sources) {
//		this.sources = sources;
//		return this;
//	}
//
//	ImportSchema setTranslateProviders(List<ImportTranslateProvider> translateProviders) {
//		this.translateProviders = translateProviders;
//		return this;
//	}
//
//	ImportSchema setWords(List<ImportWord> words) {
//		this.words = words;
//		return this;
//	}
//
//	ImportSchema setTranslates(List<ImportTranslate> translates) {
//		this.translates = translates;
//		return this;
//	}
//
//	ImportSchema setWordSources(List<ExWordSource> wordSources) {
//		this.wordSources = wordSources;
//		return this;
//	}
//
//	ImportSchema setUsers(List<ImportUser> users) {
//		this.users = users;
//		return this;
//	}

	List<ImportSource> getSources() {
		return sources;
	}

	List<ImportTranslateProvider> getTranslateProviders() {
		return translateProviders;
	}

	List<ImportWord> getWords() {
		return words;
	}

	List<ImportTranslate> getTranslates() {
		return translates;
	}

	List<ExWordSource> getWordSources() {
		return wordSources;
	}

	List<ImportUser> getUsers() {
		return users;
	}

	static class ImportUser {
		@XmlAttribute
		private String email;
		@XmlList
		private List<Integer> rememberedWords;

		String getEmail() {
			return email;
		}

		ImportUser setEmail(String email) {
			this.email = email;
			return this;
		}

		List<Integer> getRememberedWords() {
			return rememberedWords;
		}

		ImportUser setRememberedWords(List<Integer> rememberedWords) {
			this.rememberedWords = rememberedWords;
			return this;
		}
	}

	static class ImportSource {
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

		public String getContentHash() {
			return contentHash;
		}

		public Source toDomain() {
			return new Source()
				.setRawName(rawName)
				.setContentHash(contentHash)
				.setType(SourceType.valueOf(type))
				.setWordsCount(wordsCount);
		}
	}

	static class ImportTranslateProvider {
		@XmlAttribute(name = "ref")
		private int id;
		@XmlAttribute
		private String name;

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}
	}

	static class ImportWord {
		@XmlAttribute(name = "ref")
		private int id;
		@XmlAttribute
		private String name;

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}
	}

	static class ImportTranslate {
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

		public int getSourceId() {
			return sourceId;
		}

		public int getWordId() {
			return wordId;
		}

		public int getCount() {
			return count;
		}
	}
}

