package com.github.bogdanovmn.translator.web.app.admin.definition;

import com.github.bogdanovmn.common.spring.menu.MenuItem;
import com.github.bogdanovmn.common.spring.mvc.ViewTemplate;
import com.github.bogdanovmn.translator.web.app.infrastructure.AbstractVisualAdminController;
import com.github.bogdanovmn.translator.web.app.infrastructure.menu.MainMenuItem;
import com.github.bogdanovmn.translator.web.orm.entity.WordDefinitionServiceLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
class DefinitionLogController extends AbstractVisualAdminController {
	private final DefinitionLogService definitionLogService;

	@Autowired
	public DefinitionLogController(DefinitionLogService definitionLogService) {
		this.definitionLogService = definitionLogService;
	}

	@Override
	protected MenuItem currentMenuItem() {
		return MainMenuItem.DEFINITION_LOG;
	}

	@GetMapping("/definitions/log/last")
	ModelAndView lastLog() {
		return new ViewTemplate("definition/log")
			.with("statistic", definitionLogService.statistic())
			.with("entries"  , definitionLogService.lastEntries())
		.modelAndView();
	}

	@GetMapping("/definitions/log")
	ModelAndView entriesByStatus(
		@RequestParam("status") WordDefinitionServiceLog.Status status
	) {
		return new ViewTemplate("definition/log_by_status")
			.with("status"     , status)
			.with("statistic"  , definitionLogService.statistic())
			.with("entries"    , definitionLogService.entriesByStatus(status))
		.modelAndView();
	}
}
