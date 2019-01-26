package com.github.bogdanovmn.translator.web.app.admin.word;

import com.github.bogdanovmn.translator.core.ResponseNotFoundException;
import com.github.bogdanovmn.translator.core.definition.DefinitionInstance;
import com.github.bogdanovmn.translator.core.definition.PartOfSpeech;
import com.github.bogdanovmn.translator.core.definition.Sentence;
import com.github.bogdanovmn.translator.core.definition.WordDefinitionService;
import com.github.bogdanovmn.translator.service.oxforddictionaries.ResponseAnotherWordFormException;
import com.github.bogdanovmn.translator.web.orm.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
class FetchDefinitionService {
	private final WordRepository wordRepository;
	private final WordDefinitionRepository wordDefinitionRepository;
	private final UserHoldOverWordRepository holdOverWordRepository;
	private final WordDefinitionServiceLogRepository logRepository;
	private final WordDefinitionService definitionService;
	private final WordsNormalizeService wordsNormalizeService;
	private final EntityFactory entityFactory;

	@Autowired
	FetchDefinitionService(WordRepository wordRepository,
						   WordDefinitionRepository wordDefinitionRepository,
						   UserHoldOverWordRepository holdOverWordRepository,
						   WordDefinitionServiceLogRepository logRepository,
						   WordDefinitionService definitionService,
						   WordsNormalizeService wordsNormalizeService, EntityFactory entityFactory)
	{
		this.wordRepository = wordRepository;
		this.wordDefinitionRepository = wordDefinitionRepository;
		this.holdOverWordRepository = holdOverWordRepository;
		this.logRepository = logRepository;
		this.definitionService = definitionService;
		this.wordsNormalizeService = wordsNormalizeService;
		this.entityFactory = entityFactory;
	}


	@Scheduled(fixedDelay = 1800 * 1000)
	void fetchDefinitions() {
		LOG.info("Start fetch definitions");
		List<UserHoldOverWord> holdOverWords = holdOverWordRepository.allWithoutDefinition(PageRequest.of(0, 100));
		if (!holdOverWords.isEmpty()) {
			for (UserHoldOverWord holdOverWord : holdOverWords) {
				Word word = holdOverWord.getWord();
				WordDefinitionServiceLog serviceLog = new WordDefinitionServiceLog()
					.setWord(word);

				try {
					LOG.info("Fetching definition for '{}'", word.getName());
					List<DefinitionInstance> definitionInstances = definitionService.definitions(
						word.getName()
					);

					LOG.info("Success fetched: {} instances", definitionInstances.size());

					save(word, definitionInstances);
					serviceLog.done();
				}
				catch (ResponseAnotherWordFormException e) {
					LOG.info("Detect another word form: '{}'", e.getWord());
					serviceLog.anotherForm(
						String.format(
							"Found another word form: '%s' (original '%s')",
							e.getWord(), word.getName()
						)
					);
					normalizeAndSave(word, e);
					serviceLog.setMessage(
						serviceLog.getMessage() + ". Merged"
					);
				}
				catch (ResponseNotFoundException e) {
					LOG.warn("Fetching success, but word is unknown: {}", e.getMessage());
					serviceLog.notFound(e.getMessage());
					word.setBlackList(true);
					wordRepository.save(word);
				}
				catch (Exception e) {
					LOG.error("Fetching error", e);
					serviceLog.error(e.getMessage());
				}
				finally {
					logRepository.save(serviceLog);
				}
			}
		}
		LOG.info("Complete fetch definitions");
	}

	@Transactional(rollbackFor = Exception.class)
	public void normalizeAndSave(Word word, ResponseAnotherWordFormException ex) {
		wordsNormalizeService.mergeWordWithBaseValue(word, ex.getWord());
		save(word, ex.getDefinitions());
	}

	@Transactional(rollbackFor = Exception.class)
	public void save(Word word, List<DefinitionInstance> definitionInstances) {
		List<WordDefinition> wordDefinitions = new ArrayList<>();

		for (DefinitionInstance definitionInstanceImport : definitionInstances) {
			for (PartOfSpeech partOfSpeechImport : definitionInstanceImport.partOfSpeeches()) {
				for (Sentence sentenceImport : partOfSpeechImport.sentences()) {
					List<String> synonyms = new ArrayList<>();
					if (!sentenceImport.synonyms().isEmpty()) {
						synonyms.add(sentenceImport.synonyms().get(0).value());
						synonyms.addAll(sentenceImport.synonyms().get(0).more());
					}
					WordDefinition wordDefinition = new WordDefinition()
						.setWord(word)
						.setPronunciation(
							definitionInstanceImport.pronunciation()
						)
						.setPartOfSpeech(
							(WordDefinitionPartOfSpeech) entityFactory.getPersistBaseEntityWithUniqueName(
								new WordDefinitionPartOfSpeech(
									partOfSpeechImport.name()
								)
							)
						)
						.setCrossReference(
							sentenceImport.crossReference()
						)
						.setPartOfSpeechNote(
							partOfSpeechImport.grammaticalNote()
						)
						.setDescription(
							sentenceImport.description()
						)
						.setDescriptionNote(
							sentenceImport.grammaticalNote()
						)
						.setSynonyms(
							synonyms.isEmpty()
								? null
								: String.join(", ", synonyms)
						)
						.setExamples(
							sentenceImport.examples().stream()
								.map(ex ->
									new WordDefinitionExample()
										.setValue(ex.value())
										.setNote(ex.grammaticalNote())
								)
								.collect(Collectors.toList())
						);
					wordDefinitions.add(wordDefinition);
				}

			}
		}
		wordDefinitionRepository.saveAll(wordDefinitions);
	}
}
