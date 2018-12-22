package com.github.bogdanovmn.translator.web.app.admin.export;

import com.github.bogdanovmn.translator.web.orm.*;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement(name = "translatorExport")
class ExportSchema {
	private List<Source> sources;
	private List<TranslateProvider> translateProviders;
	private List<Word> words;
	private List<Translate> translates;
	private List<WordSource> wordSources;
	private List<ExportSchema.ExportUser> users = new ArrayList<>();

	@XmlElementWrapper
	@XmlElement(name = "source")
	ExportSchema setSources(List<Source> sources) {
		this.sources = sources;
		return this;
	}

	@XmlElementWrapper
	@XmlElement(name = "translateProvider")
	ExportSchema setTranslateProviders(List<TranslateProvider> translateProviders) {
		this.translateProviders = translateProviders;
		return this;
	}

	@XmlElementWrapper
	@XmlElement(name = "word")
	ExportSchema setWords(List<Word> words) {
		this.words = words;
		return this;
	}

	@XmlElementWrapper
	@XmlElement(name = "translate")
	ExportSchema setTranslates(List<Translate> translates) {
		translates.forEach(x -> x.setId(null));
		this.translates = translates;
		return this;
	}

	@XmlElementWrapper
	@XmlElement(name = "link")
	ExportSchema setWordSources(List<WordSource> wordSources) {
		wordSources.forEach(x -> x.setId(null));
		this.wordSources = wordSources;
		return this;
	}

	ExportSchema setUsers(List<User> users) {
		for (User user : users) {
			ExportUser exportUser = new ExportUser();
			List<UserRememberedWord> rememberedWords = user.getRememberedWords();
			List<UserHoldOverWord> holdOverWords = user.getHoldOverWords();
			if (!rememberedWords.isEmpty()) {
				exportUser.setRememberedWords(
					rememberedWords.stream()
						.map(x -> x.getWord().getId())
						.collect(Collectors.toList())
				);
			}
			if (!holdOverWords.isEmpty()) {
				exportUser.setHoldOverWords(
					holdOverWords.stream()
						.map(x -> x.getWord().getId())
						.collect(Collectors.toList())
				);
			}
			if (!holdOverWords.isEmpty() || !rememberedWords.isEmpty()) {
				this.users.add(
					exportUser.setEmail(user.getEmail())
				);
			}
		}
		return this;
	}

	List<Source> getSources() {
		return sources;
	}

	List<TranslateProvider> getTranslateProviders() {
		return translateProviders;
	}

	List<Word> getWords() {
		return words;
	}

	List<Translate> getTranslates() {
		return translates;
	}

	List<WordSource> getWordSources() {
		return wordSources;
	}

	@XmlElementWrapper
	@XmlElement(name = "user")
	List<ExportUser> getUsers() {
		return users;
	}

	static class ExportUser {
		private String email;

		private List<Integer> rememberedWords;
		private List<Integer> holdOverWords;

		String getEmail() {
			return email;
		}

		@XmlAttribute
		ExportUser setEmail(String email) {
			this.email = email;
			return this;
		}

		List<Integer> getRememberedWords() {
			return rememberedWords;
		}

		@XmlList
		ExportUser setRememberedWords(List<Integer> rememberedWords) {
			this.rememberedWords = rememberedWords;
			return this;
		}

		public List<Integer> getHoldOverWords() {
			return holdOverWords;
		}

		@XmlList
		public ExportUser setHoldOverWords(List<Integer> holdOverWords) {
			this.holdOverWords = holdOverWords;
			return this;
		}
	}
}

