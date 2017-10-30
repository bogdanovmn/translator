package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.web.app.config.mustache.Layout;
import com.samskivert.mustache.Mustache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;

public abstract class AbstractVisualController extends AbstractController {
	@Autowired
	private Mustache.Compiler compiler;

	@Autowired
	private StatisticService statisticService;

	@ModelAttribute("layout")
	public Mustache.Lambda layout(Map<String, Object> model) {
		return new Layout(compiler, "main");
	}

	@ModelAttribute
	public void addCommonAttributes(Model model) {
		model.addAttribute("userName", this.getUser().getName());
		model.addAllAttributes(this.statisticService.getUserWordRememberedStatistic());
	}
}
