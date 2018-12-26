package com.github.bogdanovmn.translator.web.app.admin.export;

import com.github.bogdanovmn.translator.web.orm.*;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter

@XmlRootElement(name = "translatorExport")
//@XmlAccessorType(XmlAccessType.FIELD)
class ExportSchema {
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

	@XmlElementWrapper
	@XmlElement(name = "user")
	private List<ExportSchema.ExportUser> users = new ArrayList<>();


	ExportSchema setTranslates(List<Translate> translates) {
		translates.forEach(x -> x.setId(null));
		this.translates = translates;
		return this;
	}

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

	@Getter
	@Setter
//	@XmlAccessorType(XmlAccessType.FIELD)
	static class ExportUser {
		@XmlAttribute
		private String email;
		@XmlList
		private List<Integer> rememberedWords;
		@XmlList
		private List<Integer> holdOverWords;
	}
}

