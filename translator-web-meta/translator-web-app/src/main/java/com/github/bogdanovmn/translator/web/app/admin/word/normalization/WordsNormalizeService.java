package com.github.bogdanovmn.translator.web.app.admin.word.normalization;

import com.github.bogdanovmn.common.stream.IntegerMap;
import com.github.bogdanovmn.common.stream.StringMap;
import com.github.bogdanovmn.translator.core.text.NormalizedWords;
import com.github.bogdanovmn.translator.web.orm.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WordsNormalizeService {
	@Autowired
	private WordRepository wordRepository;
	@Autowired
	private NormalizedWordCandidateRepository normalizedWordCandidateRepository;
	@Autowired
	private WordSourceRepository wordSourceRepository;
	@Autowired
	private UserRememberedWordRepository userRememberedWordRepository;
	@Autowired
	private UserHoldOverWordRepository userHoldOverWordRepository;
	@Autowired
	private UserWordProgressRepository userWordProgressRepository;

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
	public synchronized void prepareNormalizeCandidates() {
		LOG.info("Start normalized word candidates preparation");

		StringMap<Word> wordsMap = new StringMap<>(
			wordRepository.getAllByBlackListFalse(),
			Word::getName
		);

		LOG.info("Total words: {}", wordsMap.keySet().size());

		NormalizedWords normalizedWords = NormalizedWords.of(
			wordsMap.keySet()
		);

		LOG.info("Normalized words: {}", normalizedWords.get().size());

		LOG.info("Clean up normalized words table");
		normalizedWordCandidateRepository.deleteAll();

		for (String normal : normalizedWords.get()) {
			normalizedWords.wordForms(normal).ifPresent(
				forms -> {
					if (!forms.isEmpty()) {
						normalizedWordCandidateRepository.save(
							new NormalizedWordCandidate()
								.setBase(normal)
								.setForms(forms.toString())
						);
					}
				}
			);
		}
		LOG.info("Finish normalized word candidates preparation");
	}

	@Transactional(rollbackFor = Exception.class)
	public Word mergeWordWithBaseValue(Word formWord, String base) {
		Word result;
		Word baseWord = wordRepository.findFirstByName(base);
		if (baseWord != null) {
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
		LOG.info("Merge word '{}' to '{}'", formWord.getName(), word.getName());

		Set<WordSource> formSources = wordSourceRepository.findAllByWord(formWord);
		Set<WordSource> wordSources = wordSourceRepository.findAllByWord(word);

		IntegerMap<WordSource> wordSourceMap = getSourceMap(wordSources);
		IntegerMap<WordSource> formSourceMap = getSourceMap(formSources);

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

	private IntegerMap<WordSource> getSourceMap(Collection<WordSource> sources) {
		return new IntegerMap<>(sources, WordSource::getId);
	}

	List<NormalizedWordCandidate> getAllCandidates() {
		return normalizedWordCandidateRepository.findAllSortedByLength();
	}

	synchronized void approveCandidate(Integer id) {
		NormalizedWordCandidate candidate = normalizedWordCandidateRepository.getOne(id);
		candidate.getFormsWords().forEach(
			formWord -> {
				mergeWords(new Word(candidate.getBase()), formWord);
			}
		);

	}

	void deleteCandidate(Integer id) {

	}
}
