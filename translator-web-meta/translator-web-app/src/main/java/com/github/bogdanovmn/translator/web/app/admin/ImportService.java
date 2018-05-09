package com.github.bogdanovmn.translator.web.app.admin;

import com.github.bogdanovmn.translator.web.orm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImportService {
	private static final Logger LOG = LoggerFactory.getLogger(ImportService.class);

	@Autowired
	private UserRememberedWordRepository userRememberedWordRepository;
	@Autowired
	private UserHoldOverWordRepository userHoldOverWordRepository;
	@Autowired
	private WordRepository wordRepository;
	@Autowired
	private EntityFactory entityFactory;
	@Autowired
	private TranslateRepository translateRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SourceRepository sourceRepository;
	@Autowired
	private WordSourceRepository wordSourceRepository;

	private List<Source> importSources(ImportSchema importSchema, ExportWordCache exportWordCache) {
		List<Source> resultSources = new ArrayList<>();
		for (ImportSchema.ImportSource importSource : importSchema.getSources()) {
			Integer sourceExportId = importSource.getId();
			Source source = this.sourceRepository.findFirstByContentHash(importSource.getContentHash());
			if (source == null) {
				source = this.sourceRepository.save(importSource.toDomain());
				resultSources.add(source);
				LOG.info("New source '{}' import done", source.getRawName());
			}
			else {
				LOG.info("Source '{}' already exists, skip it", source.getRawName());
				continue;
			}
			final Source persistSource = source;

			LOG.info("Prepare source word links");
			List<WordSource> wordSources = importSchema.getWordSources().stream()
				.filter(x -> x.getSourceId() == sourceExportId)
				.map(x -> {
						Word word = exportWordCache.getByExportId(x.getWordId());
						word.incFrequence(x.getCount());
						word.incSourcesCount();

						return new WordSource()
							.setWord(word)
							.setSource(persistSource)
							.setCount(x.getCount());
					}
				)
				.collect(Collectors.toList());
			LOG.info("Save source word links");
			this.wordSourceRepository.save(wordSources);
			LOG.info("Source '{}' word links import done", source.getRawName());
		}
		return resultSources;
	}

	private void importTranslates(ImportSchema importSchema, ExportWordCache exportWordCache) {
		Map<Integer, TranslateProvider> translateProviderByExportId = importSchema.getTranslateProviders().stream()
			.collect(Collectors.toMap(
				ImportSchema.ImportTranslateProvider::getId,
				x -> (TranslateProvider) this.entityFactory.getPersistBaseEntityWithUniqueName(
					new TranslateProvider(x.getName())
				)
			));

		LOG.info("Translates import start");
		for (ImportSchema.ImportTranslate importTranslate : importSchema.getTranslates()) {
			Word word = exportWordCache.getByExportId(importTranslate.getWordId());
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
	}

	private List<User> importUsers(ImportSchema importSchema, ExportWordCache exportWordCache) {
		List<User> resultUsers = new ArrayList<>();
		for (ImportSchema.ImportUser importUser : importSchema.getUsers()) {
			User user = this.userRepository.findFirstByEmail(importUser.getEmail());
			if (user != null) {
				LOG.info("User {} lists import", user.getEmail());
				this.userRememberedWordRepository.removeAllByUser(user);
				this.userRememberedWordRepository.flush();
				this.userRememberedWordRepository.save(
					importUser.getRememberedWords().stream()
						.map(x ->
							new UserRememberedWord()
								.setWord(exportWordCache.getByExportId(x))
								.setUser(user)
						)
						.collect(Collectors.toSet())
				);
				LOG.info("Remembered words import: {}", importUser.getRememberedWords().size());

				this.userHoldOverWordRepository.removeAllByUser(user);
				this.userHoldOverWordRepository.flush();
				this.userHoldOverWordRepository.save(
					importUser.getHoldOverWords().stream()
						.map(x ->
							new UserHoldOverWord()
								.setWord(exportWordCache.getByExportId(x))
								.setUser(user)
						)
						.collect(Collectors.toSet())
				);
				LOG.info("HoldOver words import: {}", importUser.getHoldOverWords().size());

				this.userRepository.save(user);
				resultUsers.add(user);
				LOG.info("User '{}' import done", user.getEmail());
			}
		}
		return resultUsers;
	}

	private int importBlackList(ImportSchema importSchema, ExportWordCache exportWordCache) {
		int blackListSetCount = 0;
		for (ImportSchema.ImportWord importWord : importSchema.getWords()) {
			if (importWord.isBlackList()) {
				Word persistWord = exportWordCache.getByExportId(importWord.getId());
				if (!persistWord.isBlackList()) {
					persistWord.setBlackList(true);
					this.wordRepository.save(persistWord);
					blackListSetCount++;
				}
			}
		}
		return blackListSetCount;
	}
	@Transactional(rollbackFor = Exception.class)
	public synchronized Map<String, Object> fromFile(InputStream inputStream) throws JAXBException {
		LOG.info("Start import from file");

		// Load XML

		ImportSchema importSchema = (ImportSchema) JAXBContext.newInstance(ImportSchema.class)
			.createUnmarshaller().unmarshal(inputStream);

		LOG.info("Unmarhalling done");

		ExportWordCache exportWordCache = new ExportWordCache(
			importSchema.getWords(),
			this.entityFactory
		);

		LOG.info("Import words cache init done");

		// Sources with words

		List<Source> resultSources = importSources(importSchema, exportWordCache);

		// Translates

		LOG.info("Prepare and save translate providers");
		importTranslates(importSchema, exportWordCache);
		LOG.info("Translates import done");

		// User words lists

		List<User> resultUsers = importUsers(importSchema, exportWordCache);

		// Word in black list
		LOG.info("Black list import start");
		int count = importBlackList(importSchema, exportWordCache);

		LOG.info("Black list import done ({} affected)", count);

		return new HashMap<String, Object>() {{
			put("sources", resultSources);
			put("users", resultUsers);
		}};
	}
}
