package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.web.orm.*;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@XmlRootElement(name = "translatorExport")
class ExportSchema {
	private List<Source> sources;

	private List<TranslateProvider> translateProviders;

	private List<Word> words;

	private List<Translate> translates;

	private List<WordSource> wordSources;

	private List<ExportSchema.ExportUser> users = new ArrayList<>();

	ExportSchema setSources(List<Source> sources) {
		this.sources = sources;
		return this;
	}

	ExportSchema setTranslateProviders(List<TranslateProvider> translateProviders) {
		this.translateProviders = translateProviders;
		return this;
	}

	ExportSchema setWords(List<Word> words) {
		this.words = words;
		return this;
	}

	ExportSchema setTranslates(List<Translate> translates) {
		this.translates = translates;
		return this;
	}

	ExportSchema setWordSources(List<WordSource> wordSources) {
		this.wordSources = wordSources;
		return this;
	}

	ExportSchema setUsers(List<User> users) {
		for (User user : users) {
			Set<UserRememberedWord> rememberedWords = user.getRememberedWords();
			if (!rememberedWords.isEmpty()) {
				this.users.add(
					new ExportUser()
						.setEmail(user.getEmail())
						.setRememberedWords(
							rememberedWords.stream()
								.map(x -> x.getWord().getId())
								.collect(Collectors.toList())
						)
				);
			}
		}
		return this;
	}

	@XmlElementWrapper
	@XmlElement(name = "source")
	public List<Source> getSources() {
		return sources;
	}

	@XmlElementWrapper
	@XmlElement(name = "translateProvider")
	public List<TranslateProvider> getTranslateProviders() {
		return translateProviders;
	}

	@XmlElementWrapper
	@XmlElement(name = "word")
	public List<Word> getWords() {
		return words;
	}

	@XmlElementWrapper
	@XmlElement(name = "translate")
	public List<Translate> getTranslates() {
		return translates;
	}

	@XmlElementWrapper
	@XmlElement(name = "link")
	public List<WordSource> getWordSources() {
		return wordSources;
	}

	@XmlElementWrapper(name = "users")
	@XmlElement(name = "user")
	public List<ExportUser> getUsers() {
		return users;
	}

	public static class ExportUser {
		private String email;

		private List<Integer> rememberedWords;

		@XmlAttribute
		public String getEmail() {
			return email;
		}

		ExportUser setEmail(String email) {
			this.email = email;
			return this;
		}

		@XmlList
		public List<Integer> getRememberedWords() {
			return rememberedWords;
		}

		ExportUser setRememberedWords(List<Integer> rememberedWords) {
			this.rememberedWords = rememberedWords;
			return this;
		}
	}
}
