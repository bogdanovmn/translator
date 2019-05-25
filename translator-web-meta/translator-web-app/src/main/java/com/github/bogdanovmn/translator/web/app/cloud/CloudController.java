package com.github.bogdanovmn.translator.web.app.cloud;

import com.github.bogdanovmn.common.spring.mvc.ViewTemplate;
import com.github.bogdanovmn.translator.web.app.infrastructure.AbstractVisualController;
import com.github.bogdanovmn.translator.web.app.infrastructure.menu.MenuItem;
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
	protected MenuItem currentMenuItem() {
		return MenuItem.CLOUD;
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

	@GetMapping("/sources/{sourceId}")
	ModelAndView bySource(
		@PathVariable Integer sourceId,
		@RequestParam(required = false, defaultValue = "false", name = "all") Boolean showAll,
		@RequestParam(required = false, defaultValue = "true",  name = "unknown") Boolean showUnknown,
		@RequestParam(required = false, defaultValue = "false", name = "remembered") Boolean showRemembered
	) {
		CloudContentFilter filter = new CloudContentFilter(
			"/cloud/sources/" + sourceId,
			new HashMap<CloudContentFilterToggle, Boolean>() {{
				put(CloudContentFilterToggle.ALL, showAll);
				put(CloudContentFilterToggle.UNKNOWN, showUnknown);
				put(CloudContentFilterToggle.REMEMBERED, showRemembered);
			}}
		);
		return new ViewTemplate("cloud")
			.with("words", cloudService.sourceWords(sourceId, filter, getUser()))
			.with("filter", filter)
			.with("source", sourcesService.get(sourceId))
		.modelAndView();
	}

	@GetMapping("/sources/{sourceId}/proper-names")
	ModelAndView properNamesBySource(@PathVariable Integer sourceId) {
		CloudContentFilter filter = new CloudContentFilter(
			"/cloud/sources/" + sourceId,
			new HashMap<CloudContentFilterToggle, Boolean>() {{
				put(CloudContentFilterToggle.ALL, false);
				put(CloudContentFilterToggle.UNKNOWN, false);
				put(CloudContentFilterToggle.REMEMBERED, false);
			}}
		);
		return new ViewTemplate("cloud")
			.with("words", cloudService.sourceProperNames(sourceId))
			.with("filter", filter)
			.with("source", sourcesService.get(sourceId))
			.with("properNames", true)
		.modelAndView();
	}
}
