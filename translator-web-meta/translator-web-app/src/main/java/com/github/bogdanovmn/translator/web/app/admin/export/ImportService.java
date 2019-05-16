package com.github.bogdanovmn.translator.web.app.admin.export;

import com.github.bogdanovmn.common.spring.jpa.EntityFactory;
import com.github.bogdanovmn.translator.web.orm.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
class ImportService {
	@Autowired
	private UserRememberedWordRepository userRememberedWordRepository;
	@Autowired
	private UserHoldOverWordRepository userHoldOverWordRepository;
	@Autowired
	private WordRepository wordRepository;
	@Autowired
	private EntityFactory entityFactory;
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
			Source source = sourceRepository.findFirstByContentHash(importSource.getContentHash());
			if (source == null) {
				source = sourceRepository.save(importSource.toDomain());
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
						return new WordSource()
							.setWord(word)
							.setSource(persistSource)
							.setCount(x.getCount());
					}
				)
				.collect(Collectors.toList());
			LOG.info("Save source word links");
			wordSourceRepository.saveAll(wordSources);
			LOG.info("Source '{}' word links import done", source.getRawName());
		}
		return resultSources;
	}

	private List<User> importUsers(ImportSchema importSchema, ExportWordCache exportWordCache) {
		List<User> resultUsers = new ArrayList<>();
		for (ImportSchema.ImportUser importUser : importSchema.getUsers()) {
			User user = userRepository.findFirstByEmail(importUser.getEmail());
			if (user != null) {
				LOG.info("User {} lists import", user.getEmail());
				userRememberedWordRepository.removeAllByUser(user);
				userRememberedWordRepository.flush();
				userRememberedWordRepository.saveAll(
					importUser.getRememberedWords().stream()
						.map(x ->
							new UserRememberedWord()
								.setWord(exportWordCache.getByExportId(x))
								.setUser(user)
						)
						.collect(Collectors.toList())
				);
				LOG.info("Remembered words import: {}", importUser.getRememberedWords().size());

				userHoldOverWordRepository.removeAllByUser(user);
				userHoldOverWordRepository.flush();
				userHoldOverWordRepository.saveAll(
					importUser.getHoldOverWords().stream()
						.map(x ->
							new UserHoldOverWord()
								.setWord(exportWordCache.getByExportId(x))
								.setUser(user)
						)
						.collect(Collectors.toList())
				);
				LOG.info("HoldOver words import: {}", importUser.getHoldOverWords().size());

				userRepository.save(user);
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
					wordRepository.save(persistWord);
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
			entityFactory
		);

		LOG.info("Import words cache init done");

		// Sources with words

		List<Source> resultSources = importSources(importSchema, exportWordCache);

		// User words lists

		List<User> resultUsers = importUsers(importSchema, exportWordCache);

		// Word in black list
		LOG.info("Black list import start");
		int count = importBlackList(importSchema, exportWordCache);

		LOG.info("Black list import done ({} affected)", count);

		wordRepository.updateStatistic();
		LOG.info("Update words statistic done");

		return new HashMap<String, Object>() {{
			put("sources", resultSources);
			put("users", resultUsers);
		}};
	}
}
