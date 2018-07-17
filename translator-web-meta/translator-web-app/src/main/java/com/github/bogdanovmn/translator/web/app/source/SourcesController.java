package com.github.bogdanovmn.translator.web.app.source;

import com.github.bogdanovmn.translator.web.app.AbstractVisualController;
import com.github.bogdanovmn.translator.web.app.HeadMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
	protected HeadMenu.ITEM currentMenuItem() {
		return HeadMenu.ITEM.SOURCES;
	}

	@GetMapping("/all")
	ModelAndView listAll() {
		return new ModelAndView(
			"sources",
			"sources",
			sourcesService.getAllWithUserStatistic(
				this.getUser()
			)
		);
	}
}
