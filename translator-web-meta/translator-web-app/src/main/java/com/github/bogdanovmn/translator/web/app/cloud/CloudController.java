package com.github.bogdanovmn.translator.web.app.cloud;

import com.github.bogdanovmn.translator.web.app.AbstractVisualController;
import com.github.bogdanovmn.translator.web.app.HeadMenu;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cloud")
class CloudController extends AbstractVisualController {
	private final CloudService cloudService;

	CloudController(final CloudService cloudService) {
		this.cloudService = cloudService;
	}

	@Override
	protected HeadMenu.ITEM currentMenuItem() {
		return HeadMenu.ITEM.CLOUD;
	}

	@GetMapping
	ModelAndView all() {
		return new ModelAndView(
			"cloud",
			"words",
			cloudService.allWords()
		);
	}

	@GetMapping("/source/{id}")
	ModelAndView bySource(@PathVariable Integer id) {
		return new ModelAndView(
			"cloud",
			"words",
			cloudService.sourceWords(id)
		);
	}
}
