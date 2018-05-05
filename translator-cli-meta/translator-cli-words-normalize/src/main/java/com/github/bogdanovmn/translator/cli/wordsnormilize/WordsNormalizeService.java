package com.github.bogdanovmn.translator.cli.wordsnormilize;

import com.github.bogdanovmn.translator.core.NormalizedWords;
import com.github.bogdanovmn.translator.web.orm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class WordsNormalizeService {
	private static final Logger LOG = LoggerFactory.getLogger(WordsNormalizeService.class);

	private final WordRepository wordRepository;
	private final WordSourceRepository wordSourceRepository;
	private final UserRememberedWordRepository userRememberedWordRepository;
	private final UserHoldOverWordRepository userHoldOverWordRepository;

	@Autowired
	WordsNormalizeService(
		WordRepository wordRepository,
		WordSourceRepository wordSourceRepository,
		UserRememberedWordRepository userRememberedWordRepository,
		UserHoldOverWordRepository userHoldOverWordRepository)
	{
		this.wordRepository = wordRepository;
		this.wordSourceRepository = wordSourceRepository;
		this.userRememberedWordRepository = userRememberedWordRepository;
		this.userHoldOverWordRepository = userHoldOverWordRepository;
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

	@Transactional(rollbackFor = Exception.class)
	public synchronized void normalize() {
		LOG.info("Start normalize process");

		Map<String, Word> wordsMap = this.wordRepository.getAllByBlackListFalse().stream()
			.collect(Collectors.toMap(
				Word::getName, x -> x
			));

		LOG.info("Total words before: {}", wordsMap.keySet().size());

		NormalizedWords normalizedWords = new NormalizedWords(
			wordsMap.keySet()
		);

		LOG.info("Normalized words: {}", normalizedWords.get().size());

		for (String normal : normalizedWords.get()) {
			Set<String> forms = normalizedWords.getFormsForWord(normal);
			if (!forms.isEmpty()) {
				LOG.info("Word '{}' has forms: {}", normal, forms);

				Word normalWord = wordsMap.get(normal);

				for (String form : forms) {
					Word formWord = wordsMap.get(form);
					Set<WordSource> formSources = wordSourceRepository.findAllByWord(formWord);

					normalWord.incFrequence(formWord.getFrequence());
					mergeSources(normalWord, formSources);
					userRememberedWordRepository.removeAllByWord(formWord);
					wordRepository.delete(formWord);
				}
				wordRepository.save(normalWord);
			}
		}
		LOG.info("Finish normalize process");
	}

	private void mergeSources(Word word, Set<WordSource> formSources) {
		Set<WordSource> wordSources = wordSourceRepository.findAllByWord(word);

		Map<Integer, WordSource> wordSourceMap = getSourceMap(wordSources);
		Map<Integer, WordSource> formSourceMap = getSourceMap(formSources);

		formSourceMap.forEach(
			(id, formSource) -> {
				// Слово и его форма из одного источника
				if (wordSourceMap.containsKey(id)) {
					LOG.info("Same source '{}', increment source word counter", id);
					wordSourceMap.get(id).incCount(
						formSource.getCount()
					);
				}
				else {
					LOG.debug("New source '{}', add it for word", id);
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
