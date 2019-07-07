package com.github.bogdanovmn.translator.web.app.admin.word.normalization;

import com.github.bogdanovmn.translator.web.app.App;
import com.github.bogdanovmn.translator.web.app.admin.SourceBuilder;
import com.github.bogdanovmn.translator.web.orm.entity.Source;
import com.github.bogdanovmn.translator.web.orm.entity.WordRepository;
import com.github.bogdanovmn.translator.web.orm.entity.WordSourceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class WordsNormalizeServiceTest {
	@Autowired
	private WordsNormalizeService wordsNormalizeService;
	@Autowired
	private WordRepository wordRepository;
	@Autowired
	private WordSourceRepository wordSourceRepository;
	@Autowired
	private ObjectFactory<SourceBuilder> sourceBuilderFactory;

	@Test
	@Transactional
	public void mergeWordWithBaseValue1() {
		final String normWord = "recognize";
		final String formWord = "recognized";

		Source source1 = sourceBuilderFactory.getObject()
			.withWord(normWord, 1)
			.withWord(formWord, 5)
			.build();

		sourceBuilderFactory.getObject()
			.withWord(formWord, 10)
			.build();

		wordsNormalizeService.mergeWordWithBaseValue(
			wordRepository.findFirstByName(formWord),
			normWord
		);

		assertThat(
			"Word source count is correct",
			wordSourceRepository.findAll().stream()
				.filter(ws -> ws.getSource().getId().equals(source1.getId())
					&& ws.getWord().getName().equals(normWord)
				).findFirst().get().getCount(),
			equalTo(6)
		);

		assertThat(
			"Form word was removed",
			wordRepository.findFirstByName(formWord),
			nullValue()
		);
	}
}