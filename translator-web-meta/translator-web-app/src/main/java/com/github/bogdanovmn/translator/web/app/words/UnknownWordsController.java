package com.github.bogdanovmn.translator.web.app.words;

import com.github.bogdanovmn.common.spring.jpa.pagination.ContentPage;
import com.github.bogdanovmn.common.spring.jpa.pagination.PageMeta;
import com.github.bogdanovmn.common.spring.menu.MenuItem;
import com.github.bogdanovmn.common.spring.mvc.ViewTemplate;
import com.github.bogdanovmn.translator.web.app.infrastructure.AbstractVisualController;
import com.github.bogdanovmn.translator.web.app.infrastructure.menu.MainMenuItem;
import com.github.bogdanovmn.translator.web.app.source.SourcesService;
import com.github.bogdanovmn.translator.web.orm.entity.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(UnknownWordsController.BASE_URL)
class UnknownWordsController extends AbstractVisualController {
	final static String BASE_URL = "/unknown-words";
	private final static String QUERY_PARAM_SOURCE = "source";
	private final static String QUERY_PARAM_LITE = "lite";

	private final UnknownWordsService unknownWordsService;
	private final SourcesService sourcesService;

	@Autowired
	UnknownWordsController(UnknownWordsService unknownWordsService, SourcesService sourcesService) {
		this.unknownWordsService = unknownWordsService;
		this.sourcesService = sourcesService;
	}

	@Override
	protected MenuItem currentMenuItem() {
		return MainMenuItem.UNKNOWN_WORDS;
	}

	@GetMapping
	ModelAndView listAll(
		@RequestParam(name = QUERY_PARAM_SOURCE, required = false) Integer sourceId,
		@RequestParam(name = QUERY_PARAM_LITE  , required = false, defaultValue = "false") Boolean liteMode,
		@RequestParam(name = "page"  , required = false, defaultValue = "1") Integer pageNumber,
		@RequestParam(name = "sortBy", required = false, defaultValue = "frequency") String sortByRawValue
	) {
		ViewTemplate template;
		SortBy sortBy = SortBy.getOrDefault(sortByRawValue);
		PageMeta pageMeta = PageMeta.builder()
			.number(pageNumber)
			.sortBy(sortBy.name())
			.baseUrl(BASE_URL)
			.parameter(QUERY_PARAM_SOURCE, sourceId)
			.parameter(QUERY_PARAM_LITE, liteMode)
		.build();

		if (liteMode) {
			template = new ViewTemplate("unknown_words/lite");
			if (sourceId != null) {

				ContentPage<Word> page = unknownWordsService.getUnknownWordsBySourceLite(
					getUser(),
					sourceId,
					pageMeta
				);
				template
					.with("words", page.content())
					.with("paginationBar", page.paginationBar())
					.with("source", sourcesService.get(sourceId))
					.with("userCount", sourcesService.userRememberedWordsCount(getUser().getId(), sourceId));
			}
			else {
				ContentPage<Word> page = unknownWordsService.getAllLite(
					getUser(),
					pageMeta
				);
				template
					.with("words", page.content())
					.with("paginationBar", page.paginationBar());
			}
		}
		else {
			template = new ViewTemplate("unknown_words/extended");
			if (sourceId != null) {
				template
					.with("words", unknownWordsService.getUnknownWordsBySource(getUser(), sourceId, pageMeta))
					.with("source", sourcesService.get(sourceId))
					.with("userCount", sourcesService.userRememberedWordsCount(getUser().getId(), sourceId));
			}
			else {
				template.with("words", unknownWordsService.getAll(getUser(), pageMeta));
			}
		}

		return template
			.with("lite", liteMode)
			.with("sortByName", sortBy.equals(SortBy.name))
			.with("sortBy", sortBy)
			.modelAndView();
	}
}
