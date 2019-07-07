package com.github.bogdanovmn.translator.web.app.admin;

import com.github.bogdanovmn.translator.web.orm.entity.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SourceBuilder {
	private static int sourcesCount = 0;

	private final Source source;
	private Set<WordSource> wordSources;

	private final WordRepository wordRepository;
	private final SourceRepository sourceRepository;
	private final WordSourceRepository wordSourceRepository;

	public SourceBuilder(WordRepository wordRepository, SourceRepository sourceRepository, WordSourceRepository wordSourceRepository) {
		this.wordRepository = wordRepository;
		this.sourceRepository = sourceRepository;
		this.wordSourceRepository = wordSourceRepository;

		this.source = createSource();
	}

	public SourceBuilder withWord(String wordStr, int count) {
		Word word = wordRepository.findFirstByName(wordStr);
		if (word == null) {
			word = new Word(wordStr);
			wordRepository.save(word);
		}
		source.setWordsCount(
			source.getWordsCount() + 1
		);
		this.sourceRepository.save(source);
		wordSourceRepository.save(
			new WordSource()
				.setSource(source)
				.setWord(word)
				.setCount(count)
		);

		return this;
	}

	public Source build() {
		sourceRepository.save(source);
		return sourceRepository.getOne(source.getId());
	}

	private static Source createSource() {
		sourcesCount++;
		return new Source()
			.setRawName(generateSourceAttr("Raw name"))
			.setContentHash(generateSourceAttr("fileMd5"))
			.setType(SourceType.BOOK)
			.setTitle(generateSourceAttr("title"))
			.setAuthor(generateSourceAttr("author"))
			.setWordsCount(0);
	}

	private static String generateSourceAttr(String value) {
		return String.format("%s - %d", value, sourcesCount);
	}
}
