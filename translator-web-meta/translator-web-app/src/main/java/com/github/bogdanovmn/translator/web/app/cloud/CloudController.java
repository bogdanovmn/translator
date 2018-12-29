package com.github.bogdanovmn.translator.web.app.cloud;

import com.github.bogdanovmn.translator.web.app.infrastructure.AbstractVisualController;
import com.github.bogdanovmn.translator.web.app.infrastructure.HeadMenu;
import com.github.bogdanovmn.translator.web.app.infrastructure.ViewTemplate;
import com.github.bogdanovmn.translator.web.app.source.SourcesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
@RequestMapping("/cloud")
class CloudController extends AbstractVisualController {
	private final CloudService cloudService;
	private final SourcesService sourcesService;

	CloudController(final CloudService cloudService, SourcesService sourcesService) {
		this.cloudService = cloudService;
		this.sourcesService = sourcesService;
	}

	@Override
	protected HeadMenu.ITEM currentMenuItem() {
		return HeadMenu.ITEM.CLOUD;
	}

	@GetMapping
	ModelAndView all(
		@RequestParam(required = false, defaultValue = "false", name = "all") Boolean showAll,
		@RequestParam(required = false, defaultValue = "true",  name = "unknown") Boolean showUnknown,
		@RequestParam(required = false, defaultValue = "false", name = "remembered") Boolean showRemembered
	) {
		CloudContentFilter filter = new CloudContentFilter(
			"/cloud",
			new HashMap<CloudContentFilterToggle, Boolean>() {{
				put(CloudContentFilterToggle.ALL, showAll);
				put(CloudContentFilterToggle.UNKNOWN, showUnknown);
				put(CloudContentFilterToggle.REMEMBERED, showRemembered);
			}}
		);
		return new ViewTemplate("cloud")
			.with("words", cloudService.allWords(filter, getUser()))
			.with("filter", filter)
		.modelAndView();
	}

	@GetMapping("/source/{id}")
	ModelAndView bySource(
		@PathVariable Integer id,
		@RequestParam(required = false, defaultValue = "false", name = "all") Boolean showAll,
		@RequestParam(required = false, defaultValue = "true",  name = "unknown") Boolean showUnknown,
		@RequestParam(required = false, defaultValue = "false", name = "remembered") Boolean showRemembered
	) {
		CloudContentFilter filter = new CloudContentFilter(
			"/cloud/source/" + id,
			new HashMap<CloudContentFilterToggle, Boolean>() {{
				put(CloudContentFilterToggle.ALL, showAll);
				put(CloudContentFilterToggle.UNKNOWN, showUnknown);
				put(CloudContentFilterToggle.REMEMBERED, showRemembered);
			}}
		);
		return new ViewTemplate("cloud")
			.with("words", cloudService.sourceWords(id, filter, getUser()))
			.with("filter", filter)
			.with("source", sourcesService.get(id))
		.modelAndView();
	}
}
