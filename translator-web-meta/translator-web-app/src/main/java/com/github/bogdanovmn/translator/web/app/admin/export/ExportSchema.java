package com.github.bogdanovmn.translator.web.app.admin.export;

import com.github.bogdanovmn.translator.web.orm.entity.User;
import com.github.bogdanovmn.translator.web.orm.entity.UserRememberedWord;
import com.github.bogdanovmn.translator.web.orm.entity.Word;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@XmlRootElement(name = "translatorExport")
class ExportSchema {
	private Set<ExportWord> words;

	private List<ExportSchema.ExportUser> users = new ArrayList<>();

	ExportSchema setUsers(List<User> users) {
		words = new HashSet<>();
		for (User user : users) {
			ExportUser exportUser = new ExportUser();
			List<UserRememberedWord> rememberedWords = user.getRememberedWords();
			if (!rememberedWords.isEmpty()) {
				exportUser.setRememberedWords(
					rememberedWords.stream()
						.map(x -> x.getWord().getId())
						.collect(Collectors.toList())
				);
				rememberedWords.forEach(
					rw -> words.add(new ExportWord(rw.getWord()))
				);
				this.users.add(
					exportUser.setEmail(user.getEmail())
				);
			}
		}
		return this;
	}

	@XmlElementWrapper
	@XmlElement(name = "word")
	public Set<ExportWord> getWords() {
		return this.words;
	}

	@XmlElementWrapper
	@XmlElement(name = "user")
	public List<ExportUser> getUsers() {
		return this.users;
	}

	static class ExportUser {

		private String email;
		private List<Integer> rememberedWords;

		@XmlAttribute
		public String getEmail() {
			return this.email;
		}

		@XmlList
		public List<Integer> getRememberedWords() {
			return this.rememberedWords;
		}

		public ExportUser setEmail(String email) {
			this.email = email;
			return this;
		}

		public ExportUser setRememberedWords(List<Integer> rememberedWords) {
			this.rememberedWords = rememberedWords;
			return this;
		}
	}

	@EqualsAndHashCode(of = "ref")
	static class ExportWord {
		private Integer ref;
		private String name;

		ExportWord(Word word) {
			this.ref = word.getId();
			this.name = word.getName();
		}

		@XmlAttribute
		public String getName() {
			return name;
		}

		public ExportWord setName(String name) {
			this.name = name;
			return this;
		}

		@XmlAttribute
		public Integer getRef() {
			return ref;
		}

		public ExportWord setRef(Integer ref) {
			this.ref = ref;
			return this;
		}
	}
}

