package com.github.bogdanovmn.translator.cli.wordsnormilize;

import com.github.bogdanovmn.translator.core.NormalizedWords;
import com.github.bogdanovmn.translator.web.orm.Word;
import com.github.bogdanovmn.translator.web.orm.WordRepository;
import com.github.bogdanovmn.translator.web.orm.WordSource;
import com.github.bogdanovmn.translator.web.orm.WordSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class WordsNormalizeService {
	private final WordRepository wordRepository;
	private final WordSourceRepository wordSourceRepository;

	@Autowired
	public WordsNormalizeService(WordRepository wordRepository, WordSourceRepository wordSourceRepository) {
		this.wordRepository = wordRepository;
		this.wordSourceRepository = wordSourceRepository;
	}

	void dry() {
		Set<Word> words = this.wordRepository.getAllByBlackListFalse();
		NormalizedWords normalizedWords = new NormalizedWords(
			words.stream()
				.map(Word::getName)
				.collect(Collectors.toSet())
		);

		normalizedWords.printWordsWithForms();
	}

	@Transactional
	synchronized void normalize() {
		Map<String, Word> wordsMap = this.wordRepository.getAllByBlackListFalse().stream()
			.collect(Collectors.toMap(
				Word::getName, x -> x
			));

		NormalizedWords normalizedWords = new NormalizedWords(
			wordsMap.keySet()
		);

		for (String normal : normalizedWords.get()) {
			Set<String> forms = normalizedWords.getFormsForWord(normal);
			Word normalWord = wordsMap.get(normal);

			for (String form : forms) {
				Word formWord = wordsMap.get(form);
				Set<WordSource> formSources = formWord.getWordSources();

				normalWord.incFrequence(formWord.getFrequence());
				mergeSources(normalWord, formSources);
				wordRepository.delete(formWord);
			}
			wordRepository.save(normalWord);
		}
	}

	private void mergeSources(Word word, Set<WordSource> formSources) {
		Set<WordSource> wordSources = word.getWordSources();

		Map<Integer, WordSource> wordSourceMap = getSourceMap(wordSources);
		Map<Integer, WordSource> formSourceMap = getSourceMap(formSources);

		formSourceMap.forEach(
			(id, formSource) -> {
				// Слово и его форма из одного источника
				if (wordSourceMap.containsKey(id)) {
					wordSourceMap.get(id).incCount(
						formSource.getCount()
					);
				}
				else {
					wordSources.add(
						new WordSource()
							.setWord(word)
							.setCount(formSource.getCount())
							.setSource(formSource.getSource())
					);
				}
				wordSourceRepository.delete(formSource);
			}
		);
	}

	private Map<Integer, WordSource> getSourceMap(Collection<WordSource> sources) {
		return sources.stream()
			.collect(Collectors.toMap(
				WordSource::getId, x -> x
			));
	}
}
