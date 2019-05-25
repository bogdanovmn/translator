package com.github.bogdanovmn.translator.web.app.infrastructure;

import com.github.bogdanovmn.translator.web.app.StatisticService;
import com.github.bogdanovmn.translator.web.app.infrastructure.config.mustache.Layout;
import com.github.bogdanovmn.translator.web.app.infrastructure.menu.MenuBuilder;
import com.github.bogdanovmn.translator.web.app.infrastructure.menu.MenuItem;
import com.samskivert.mustache.Mustache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;

public abstract class AbstractVisualController extends AbstractController {
	@Autowired
	private Mustache.Compiler compiler;

	@Autowired
	private StatisticService statisticService;

	@Autowired
	private MenuBuilder menuBuilder;

	@Value("${server.servlet.context-path:}")
	private String contextPath;

	@ModelAttribute("layout")
	public Mustache.Lambda layout(Map<String, Object> model) {
		return new Layout(compiler, "main", contextPath);
	}

	@ModelAttribute
	public void addCommonAttributes(Model model) {
		model.addAttribute(
			"menu",
			menuBuilder
				.setSelectedItem(currentMenuItem())
				.setUser(getUser())
				.build()
		);
		model.addAttribute("userName", getUser().getName());
		model.addAllAttributes(statisticService.getUserWordRememberedStatistic(getUser()));
	}

	protected abstract MenuItem currentMenuItem();
	protected MenuItem currentAdminMenuItem() { return MenuItem.NONE; }
}
