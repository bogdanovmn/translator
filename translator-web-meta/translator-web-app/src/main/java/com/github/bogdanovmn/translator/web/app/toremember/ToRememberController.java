package com.github.bogdanovmn.translator.web.app.toremember;

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
@RequestMapping("/to-remember")
class ToRememberController extends AbstractVisualController {
	private final ToRememberService toRememberService;
	private final SourcesService sourcesService;

	@Autowired
	ToRememberController(ToRememberService toRememberService, SourcesService sourcesService) {
		this.toRememberService = toRememberService;
		this.sourcesService = sourcesService;
	}

	@Override
	protected HeadMenu.ITEM currentMenuItem() {
		return HeadMenu.ITEM.TO_REMEMBER;
	}

	@GetMapping("/all")
	ModelAndView listAll() {
		return new ViewTemplate("to_remember")
			.with("words", toRememberService.getAll())
		.modelAndView();
	}

	@GetMapping("/source/{id}")
	ModelAndView source(@PathVariable Integer id) {
		return new ViewTemplate("to_remember")
			.with("words", toRememberService.getAllBySource(id))
			.with("source", sourcesService.get(id))
			.with("userCount", sourcesService.userRememberedWordsCount(getUser().getId(), id))
		.modelAndView();
	}
}
