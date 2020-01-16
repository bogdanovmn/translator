package com.github.bogdanovmn.translator.web.app.source;

import com.github.bogdanovmn.common.spring.menu.MenuItem;
import com.github.bogdanovmn.common.spring.mvc.ViewTemplate;
import com.github.bogdanovmn.translator.web.app.infrastructure.AbstractVisualController;
import com.github.bogdanovmn.translator.web.app.infrastructure.menu.MainMenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/sources")
class SourcesController extends AbstractVisualController {
	private final SourcesService sourcesService;

	@Autowired
	SourcesController(SourcesService sourcesService) {
		this.sourcesService = sourcesService;
	}

	@Override
	protected MenuItem currentMenuItem() {
		return MainMenuItem.SOURCES;
	}

	@GetMapping
	ModelAndView listAll() {
		return new ViewTemplate("sources")
			.with("sources", sourcesService.getAllWithUserStatistic(getUser()))
		.modelAndView();
	}

	@GetMapping("/{sourceId}/unknown-words")
	ModelAndView source(@PathVariable Integer sourceId) {
		return new ViewTemplate("unknown_words")
			.with("words", sourcesService.getUnknownWordsBySource(getUser(), sourceId))
			.with("source", sourcesService.get(sourceId))
			.with("userCount", sourcesService.userRememberedWordsCount(getUser().getId(), sourceId))
			.modelAndView();
	}
}
