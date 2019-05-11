package com.github.bogdanovmn.translator.web.app.words;

import com.github.bogdanovmn.translator.web.app.infrastructure.AbstractVisualController;
import com.github.bogdanovmn.translator.web.app.infrastructure.HeadMenu;
import com.github.bogdanovmn.translator.web.app.infrastructure.ViewTemplate;
import com.github.bogdanovmn.translator.web.app.source.SourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/unknown-words")
class UnknownWordsController extends AbstractVisualController {
	private final UnknownWordsService unknownWordsService;

	@Autowired
	UnknownWordsController(UnknownWordsService unknownWordsService) {
		this.unknownWordsService = unknownWordsService;
	}

	@Override
	protected HeadMenu.ITEM currentMenuItem() {
		return HeadMenu.ITEM.UNKNOWN_WORDS;
	}

	@GetMapping
	ModelAndView listAll() {
		return new ViewTemplate("unknown_words")
			.with("words", unknownWordsService.getAll(getUser()))
		.modelAndView();
	}
}
