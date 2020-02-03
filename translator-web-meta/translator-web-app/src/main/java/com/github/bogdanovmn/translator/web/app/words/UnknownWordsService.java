package com.github.bogdanovmn.translator.web.app.words;

import com.github.bogdanovmn.common.spring.jpa.pagination.ContentPage;
import com.github.bogdanovmn.common.spring.jpa.pagination.PageMeta;
import com.github.bogdanovmn.translator.web.orm.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
class UnknownWordsService {
	private final static int WORDS_PER_PAGE = 10;
	private final static int LITE_MOD_PER_PAGE_MULTIPLIER = 20;
	@Autowired
	private UserRememberedWordRepository userRememberedWordRepository;
	@Autowired
	private UserHoldOverWordRepository userHoldOverWordRepository;
	@Autowired
	private UserWordProgressRepository userWordProgressRepository;
	@Autowired
	private WordRepository wordRepository;

	List<WordRepository.WordWithUserProgress> getAll(User user, PageMeta pageMeta) {
		return wordRepository.unknownByAllSources(
			user.getId(),
			PageRequest.of(
				0,
				WORDS_PER_PAGE,
				SortBy.name.name().equals(pageMeta.getSortBy())
					? Sort.by("name")
					: Sort.by("sourcesCount").descending()
						.and(Sort.by("frequency").descending())
			)
		);
	}

	ContentPage<Word> getAllLite(User user, PageMeta pageMeta) {
		return new ContentPage<>(
			wordRepository.unknownByAllSourcesLite(
				user.getId(),
				PageRequest.of(
					pageMeta.getNumber() - 1,
					WORDS_PER_PAGE * LITE_MOD_PER_PAGE_MULTIPLIER,
					Sort.by(
						pageMeta.getSortBy()
					)
				)
			),
			pageMeta
		);
	}

	List<WordRepository.WordBySourceWithUserProgress> getUnknownWordsBySource(User user, Integer sourceId, PageMeta pageMeta) {
		return wordRepository.unknownBySource(
			user.getId(),
			sourceId,
			PageRequest.of(
				0,
				WORDS_PER_PAGE,
				SortBy.name.name().equals(pageMeta.getSortBy())
					? Sort.by("w.name")
					: Sort.by("count").descending()
			)
		);
	}

	ContentPage<Word> getUnknownWordsBySourceLite(User user, Integer sourceId, PageMeta pageMeta) {
		return new ContentPage<>(
			wordRepository.unknownBySourceLite(
				user.getId(),
				sourceId,
				PageRequest.of(
					pageMeta.getNumber() - 1,
					WORDS_PER_PAGE * LITE_MOD_PER_PAGE_MULTIPLIER,
					SortBy.name.name().equals(pageMeta.getSortBy())
						? Sort.by("w.name")
						: Sort.by("count").descending()
				)
			),
			pageMeta
		);
	}

	void rememberWord(User user, Integer wordId) {
		if (null == userRememberedWordRepository.findFirstByUserAndWordId(user, wordId)) {
			userRememberedWordRepository.save(
				new UserRememberedWord()
					.setUser(user)
					.setWord(new Word(wordId))
					.setUpdated(new Date())
			);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void holdOverWord(User user, Integer wordId) {
		if (null == userHoldOverWordRepository.findFirstByUserAndWordId(user, wordId)) {
			Word word = new Word(wordId);
			userHoldOverWordRepository.save(
				new UserHoldOverWord()
					.setUser(user)
					.setWord(word)
			);
			UserWordProgress progress = userWordProgressRepository.findByUserAndWord(user, word);
			if (null == progress) {
				progress = new UserWordProgress()
					.setUser(user)
					.setWord(word);
			}
			progress.incHoldOverCount();
			userWordProgressRepository.save(progress);
		}
	}
}
