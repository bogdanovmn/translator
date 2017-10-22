package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.web.orm.*;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	private List<ExportSchema.ExportUser> users = new ArrayList<>();

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

	public ExportSchema setUsers(List<User> users) {
		for (User user : users) {
			this.users.add(
				new ExportUser()
					.setEmail(user.getEmail())
					.setRememberedWords(
						user.getRememberedWords().stream()
							.map(x -> x.getWord().getId())
							.collect(Collectors.toList())
					)
			);
		}
		return this;
	}

	public static class ExportUser {
		private String email;

		private List<Integer> rememberedWords;

		@XmlAttribute
		public String getEmail() {
			return email;
		}

		public ExportUser setEmail(String email) {
			this.email = email;
			return this;
		}

		@XmlList
		public List<Integer> getRememberedWords() {
			return rememberedWords;
		}

		public ExportUser setRememberedWords(List<Integer> rememberedWords) {
			this.rememberedWords = rememberedWords;
			return this;
		}
	}
}
