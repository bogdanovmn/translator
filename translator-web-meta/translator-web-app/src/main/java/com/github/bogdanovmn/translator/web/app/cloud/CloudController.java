package com.github.bogdanovmn.translator.web.app.cloud;

import com.github.bogdanovmn.translator.web.app.AbstractVisualController;
import com.github.bogdanovmn.translator.web.app.HeadMenu;
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

	CloudController(final CloudService cloudService) {
		this.cloudService = cloudService;
	}

	@Override
	protected HeadMenu.ITEM currentMenuItem() {
		return HeadMenu.ITEM.CLOUD;
	}

	@GetMapping
	ModelAndView all(
		@RequestParam(required = false, defaultValue = "false", name = "all") Boolean showAll,
		@RequestParam(required = false, defaultValue = "true",  name = "unknown") Boolean showUnknown,
		@RequestParam(required = false, defaultValue = "false", name = "known") Boolean showKnown
	) {
		CloudContentFilter filter = new CloudContentFilter(
			"/cloud",
			new HashMap<CloudContentFilterToggle, Boolean>() {{
				put(CloudContentFilterToggle.ALL, showAll);
				put(CloudContentFilterToggle.UNKNOWN, showUnknown);
				put(CloudContentFilterToggle.KNOWN, showKnown);
			}}
		);
		return new ModelAndView(
			"cloud",
			new HashMap<String, Object>() {{
				put("words", cloudService.allWords(filter, getUser()));
				put("filter", filter);
			}}
		);
	}

	@GetMapping("/source/{id}")
	ModelAndView bySource(
		@PathVariable Integer id,
		@RequestParam(required = false, defaultValue = "false", name = "all") Boolean showAll,
		@RequestParam(required = false, defaultValue = "true",  name = "unknown") Boolean showUnknown,
		@RequestParam(required = false, defaultValue = "false", name = "known") Boolean showKnown
	) {
		CloudContentFilter filter = new CloudContentFilter(
			"/cloud/source/" + id,
			new HashMap<CloudContentFilterToggle, Boolean>() {{
				put(CloudContentFilterToggle.ALL, showAll);
				put(CloudContentFilterToggle.UNKNOWN, showUnknown);
				put(CloudContentFilterToggle.KNOWN, showKnown);
			}}
		);
		return new ModelAndView(
			"cloud",
			new HashMap<String, Object>() {{
				put("words", cloudService.sourceWords(id, filter, getUser()));
				put("filter", filter);
			}}
		);
	}
}
