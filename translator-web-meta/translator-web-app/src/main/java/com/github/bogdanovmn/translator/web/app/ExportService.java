package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.web.orm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
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
		// Load XML

		Unmarshaller marshaller = JAXBContext.newInstance(ExportSchema.class)
			.createUnmarshaller();

		ExportSchema exportSchema = (ExportSchema) marshaller.unmarshal(inputStream);

		Map<Integer, Word> wordsByExportId = exportSchema.getWords().stream()
			.collect(Collectors.toMap(
				BaseEntity::getId, x -> x
			));
		wordsByExportId.forEach((id, word) -> word.setId(null));

		// Sources with words


		List<Source> sources = exportSchema.getSources();
		for (Source source : sources) {
			Integer sourceExportId = source.getId();
			Source persistSource = this.sourceRepository.findFirstByContentHash(source.getContentHash());
			if (persistSource == null) {
				persistSource = this.sourceRepository.save(source);
			}
			else {
				continue;
			}

			List<WordSource> wordSources = exportSchema.getWordSources().stream()
				.filter(x -> Objects.equals(x.getSource().getId(), sourceExportId))
				.collect(Collectors.toList());

			for (WordSource wordSource : wordSources) {
				Integer exportWordId = wordSource.getWord().getId();

				Word word = (Word) this.entityFactory.getPersistBaseEntityWithUniqueName(
					wordsByExportId.get(exportWordId)
				);

				wordsByExportId.put(exportWordId, word);

				this.wordSourceRepository.save(
					wordSource
						.setWord(word)
						.setSource(persistSource)
				);
			}
		}

		// Translates

		Map<Integer, TranslateProvider> translateProviderByExportId = exportSchema.getTranslateProviders().stream()
			.collect(Collectors.toMap(
				BaseEntity::getId, x -> (TranslateProvider) this.entityFactory.getPersistBaseEntityWithUniqueName(x)
			));
		translateProviderByExportId.forEach((id, translateProvider) -> translateProvider.setId(null));

		for (Translate translate : exportSchema.getTranslates()) {
			Word word = wordsByExportId.get(translate.getWord().getId());
			if (word.getId() == null) {
				word = (Word) this.entityFactory.getPersistBaseEntityWithUniqueName(word);
			}

			TranslateProvider translateProvider = (TranslateProvider) this.entityFactory.getPersistBaseEntityWithUniqueName(
				translateProviderByExportId.get(translate.getProvider().getId())
			);

			Set<Translate> wordTranslates = word.getTranslates();
			if (!wordTranslates.contains(translate)) {
				this.translateRepository.save(
					translate
						.setProvider(translateProvider)
						.setWord(word));
			}
		}

		// User words lists

		for (ExportSchema.ExportUser exportUser : exportSchema.getUsers()) {
			User user = this.userRepository.findFirstByEmail(exportUser.getEmail());
			if (user != null) {
				user.setRememberedWords(
					exportUser.getRememberedWords().stream()
						.map(x -> {
							Word word = wordsByExportId.get(x);
							if (word.getId() == null) {
								word = (Word) this.entityFactory.getPersistBaseEntityWithUniqueName(word);
							}
							return new UserRememberedWord()
								.setWord(word);
						})
						.collect(Collectors.toSet())
				);
			}
		}


		return new HashMap<String, Object>() {{
			put("sources", sources);
			put("sources", exportSchema.getUsers());
		}};
	}
}
