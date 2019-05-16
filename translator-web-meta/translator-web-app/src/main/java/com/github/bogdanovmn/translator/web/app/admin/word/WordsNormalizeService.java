package com.github.bogdanovmn.translator.web.app.admin.word;

import com.github.bogdanovmn.translator.core.text.NormalizedWords;
import com.github.bogdanovmn.translator.web.orm.entity.*;
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
public class WordsNormalizeService {
	private static final Logger LOG = LoggerFactory.getLogger(WordsNormalizeService.class);

	private final WordRepository wordRepository;
	private final WordSourceRepository wordSourceRepository;
	private final UserRememberedWordRepository userRememberedWordRepository;
	private final UserHoldOverWordRepository userHoldOverWordRepository;
	private final UserWordProgressRepository userWordProgressRepository;

	@Autowired
	public WordsNormalizeService(
		WordRepository wordRepository,
		WordSourceRepository wordSourceRepository,
		UserRememberedWordRepository userRememberedWordRepository,
		UserHoldOverWordRepository userHoldOverWordRepository, UserWordProgressRepository userWordProgressRepository)
	{
		this.wordRepository = wordRepository;
		this.wordSourceRepository = wordSourceRepository;
		this.userRememberedWordRepository = userRememberedWordRepository;
		this.userHoldOverWordRepository = userHoldOverWordRepository;
		this.userWordProgressRepository = userWordProgressRepository;
	}

	public void dry() {
		Set<Word> words = this.wordRepository.getAllByBlackListFalse();
		NormalizedWords normalizedWords = NormalizedWords.of(
			words.stream()
				.map(Word::getName)
				.collect(Collectors.toSet())
		);

		System.out.println(
			normalizedWords.wordsWithFormsStatistic()
		);
	}

	@Transactional(rollbackFor = Exception.class)
	public synchronized void normalizeAll() {
		LOG.info("Start normalize process");

		Map<String, Word> wordsMap = this.wordRepository.getAllByBlackListFalse().stream()
			.collect(Collectors.toMap(
				Word::getName, x -> x
			));

		LOG.info("Total words before: {}", wordsMap.keySet().size());

		NormalizedWords normalizedWords = NormalizedWords.of(
			wordsMap.keySet()
		);

		LOG.info("Normalized words: {}", normalizedWords.get().size());

		for (String normal : normalizedWords.get()) {
			normalizedWords.wordForms(normal).ifPresent(
				forms -> {
					if (!forms.isEmpty()) {
						LOG.info("Word '{}' has forms: {}", normal, forms);

						Word normalWord = wordsMap.get(normal);

						for (String form : forms) {
							Word formWord = wordsMap.get(form);
							normalWord.incFrequency(formWord.getFrequence());

							mergeWords(normalWord, formWord);
						}
						wordRepository.save(normalWord);
					}
				}
			);
		}
		LOG.info("Finish normalize process");
	}

	@Transactional(rollbackFor = Exception.class)
	public Word mergeWordWithBaseValue(Word formWord, String base) {
		Word result;
		Word baseWord = wordRepository.findFirstByName(base);
		if (baseWord != null) {
			LOG.info("Merge word '{}' to '{}'", formWord.getName(), base);
			mergeWords(baseWord, formWord);
			result = baseWord;
		}
		else {
			LOG.info("Rename word '{}' to '{}'", formWord.getName(), base);
			formWord.setName(base);
			wordRepository.save(formWord);
			result = formWord;
		}
		return result;
	}

	@Transactional(rollbackFor = Exception.class)
	public void mergeWords(Word word, Word formWord) {
		Set<WordSource> formSources = wordSourceRepository.findAllByWord(formWord);
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
					LOG.info("New source '{}', add it for word", id);
					wordSources.add(
						new WordSource()
							.setWord(word)
							.setCount(formSource.getCount())
							.setSource(formSource.getSource())
					);
					word.incSourceCount();
				}
			}
		);
		wordSourceRepository.saveAll(wordSources);
		wordSourceRepository.deleteAll(formSources);
		userRememberedWordRepository.deleteAllByWord(formWord);
		userHoldOverWordRepository.removeAllByWord(formWord);
		userWordProgressRepository.removeAllByWord(formWord);
		wordRepository.delete(formWord);
	}

	private Map<Integer, WordSource> getSourceMap(Collection<WordSource> sources) {
		return sources.stream()
			.collect(Collectors.toMap(
				WordSource::getId, x -> x
			));
	}
}
