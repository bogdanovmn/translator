package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.web.orm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExportService {
	@Autowired
	private UserRememberedWordRepository userRememberedWordRepository;
	@Autowired
	private WordRepository wordRepository;
	@Autowired
	private EntityFactory entityFactory;
	@Autowired
	private TranslateRepository translateRepository;
	@Autowired
	private TranslateProviderRepository translateProviderRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SourceRepository sourceRepository;
	@Autowired
	private WordSourceRepository wordSourceRepository;

	public void export(OutputStream outputStream)
		throws JAXBException
	{
		Marshaller marshaller = JAXBContext.newInstance(ExportSchema.class)
			.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

		marshaller.marshal(
			new ExportSchema()
				.setSources(
					this.sourceRepository.findAll()
				)
				.setTranslateProviders(
					this.translateProviderRepository.findAll()
				)
				.setWords(
					this.wordRepository.findAll()
				)
				.setTranslates(
					this.translateRepository.findAll()
				)
				.setWordSources(
					this.wordSourceRepository.findAll()
				)
				.setUsers(
					this.userRepository.findAll()
				),
			outputStream
		);
	}

	@Transactional
	public Map<String, Object> importFromFile(InputStream inputStream)
		throws JAXBException
	{
		List<Source> resultSources = new ArrayList<>();
		List<User> resultUsers = new ArrayList<>();

		// Load XML

		ImportSchema importSchema = (ImportSchema) JAXBContext.newInstance(ImportSchema.class)
			.createUnmarshaller().unmarshal(inputStream);

		Map<Integer, Word> wordsByExportId = importSchema.getWords().stream()
			.collect(Collectors.toMap(
				ImportSchema.ImportWord::getId,
				x -> (Word) this.entityFactory.getPersistBaseEntityWithUniqueName(
					new Word(x.getName())
				)
			));

		// Sources with words

		for (ImportSchema.ImportSource importSource : importSchema.getSources()) {
			Integer sourceExportId = importSource.getId();
			Source source = this.sourceRepository.findFirstByContentHash(importSource.getContentHash());
			if (source == null) {
				source = this.sourceRepository.save(importSource.toDomain());
				resultSources.add(source);
			}
			else {
				continue;
			}
			final Source persistSource = source;

			List<WordSource> wordSources = importSchema.getWordSources().stream()
				.filter(x -> x.getSourceId() == sourceExportId)
				.map(x -> {
					Word word = wordsByExportId.get(x.getWordId());
					word.incFrequence(x.getCount());
					word.incSourcesCount();

					return new WordSource()
						.setWord(word)
						.setSource(persistSource)
						.setCount(x.getCount());
					}
				)
				.collect(Collectors.toList());
			this.wordSourceRepository.save(wordSources);
		}

		// Translates

		Map<Integer, TranslateProvider> translateProviderByExportId = importSchema.getTranslateProviders().stream()
			.collect(Collectors.toMap(
				ImportSchema.ImportTranslateProvider::getId,
				x -> (TranslateProvider) this.entityFactory.getPersistBaseEntityWithUniqueName(
					new TranslateProvider(x.getName())
				)
			));

		for (ImportSchema.ImportTranslate importTranslate : importSchema.getTranslates()) {
			Word word = wordsByExportId.get(importTranslate.getWordId());
			Translate translate = new Translate()
				.setValue(importTranslate.getValue())
				.setWord(word)
				.setProvider(
					translateProviderByExportId.get(importTranslate.getProviderId())
				);

			Set<Translate> wordTranslates = word.getTranslates();
			if (!wordTranslates.contains(translate)) {
				this.translateRepository.save(translate);
			}
		}

		// User words lists

		for (ImportSchema.ImportUser importUser : importSchema.getUsers()) {
			User user = this.userRepository.findFirstByEmail(importUser.getEmail());
			if (user != null) {
				user.getRememberedWords().addAll(
					importUser.getRememberedWords().stream()
						.map(x ->
							new UserRememberedWord()
								.setWord(wordsByExportId.get(x))
								.setUser(user)
						)
						.collect(Collectors.toSet())
				);
				this.userRepository.save(user);
				resultUsers.add(user);
			}
		}


		return new HashMap<String, Object>() {{
			put("sources", resultSources);
			put("users", resultUsers);
		}};
	}
}
