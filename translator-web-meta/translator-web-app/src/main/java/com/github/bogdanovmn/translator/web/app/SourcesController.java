package com.github.bogdanovmn.translator.web.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
@RequestMapping("/sources")
public class SourcesController extends AbstractVisualController {
	private final SourcesService sourcesService;

	@Autowired
	public SourcesController(SourcesService sourcesService) {
		this.sourcesService = sourcesService;
	}

	@Override
	protected HeadMenu.ITEM currentMenuItem() {
		return HeadMenu.ITEM.TO_REMEMBER;
	}

	@GetMapping("/all")
	public ModelAndView listAll() {
		return new ModelAndView(
			"sources",
			new HashMap<String, Object>() {{
				put("sources", sourcesService.getAll());
			}}
		);
	}
}
