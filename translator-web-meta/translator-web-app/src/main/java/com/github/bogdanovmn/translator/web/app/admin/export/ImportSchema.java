package com.github.bogdanovmn.translator.web.app.admin.export;

import lombok.Getter;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Getter

@XmlRootElement(name = "translatorExport")
class ImportSchema {

	@XmlElementWrapper
	@XmlElement(name = "word")
	private List<ImportWord> words;

	@XmlElementWrapper
	@XmlElement(name = "user")
	private List<ImportUser> users = new ArrayList<>();

	@Getter
	static class ImportUser {
		@XmlAttribute
		private String email;
		@XmlList
		private List<Integer> rememberedWords;
	}

	@Getter
	static class ImportWord {
		@XmlAttribute
		private int ref;
		@XmlAttribute
		private String name;
		@XmlAttribute
		private boolean blackList;
	}
}

