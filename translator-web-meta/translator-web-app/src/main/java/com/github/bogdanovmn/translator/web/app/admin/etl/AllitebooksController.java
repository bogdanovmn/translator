package com.github.bogdanovmn.translator.web.app.admin.etl;

import com.github.bogdanovmn.common.spring.mvc.ViewTemplate;
import com.github.bogdanovmn.translator.etl.allitbooks.orm.DownloadStatus;
import com.github.bogdanovmn.translator.web.app.infrastructure.AbstractVisualAdminController;
import com.github.bogdanovmn.translator.web.app.infrastructure.menu.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;

@Controller
class AllitebooksController extends AbstractVisualAdminController {
	private final AllitebooksService allitebooksService;

	@Autowired
	public AllitebooksController(AllitebooksService allitebooksService) {
		this.allitebooksService = allitebooksService;
	}

	@Override
	protected MenuItem currentMenuItem() {
		return MenuItem.ETL;
	}

	@GetMapping("/etl/download-process")
	ModelAndView downloadProcess() {
		return new ViewTemplate("etl/download_process")
			.with("statistic"  , allitebooksService.statistic())
			.with("activeItems", allitebooksService.activeItems())
		.modelAndView();
	}

	@GetMapping("/etl/download-process/{status}")
	ModelAndView downloadProcessByStatus(@PathVariable("status") DownloadStatus status) {
		return new ViewTemplate("etl/download_process_by_status")
			.with("status", status)
			.with("statistic", allitebooksService.statistic())
			.with("items"    , allitebooksService.itemsByStatus(status))
		.modelAndView();
	}

	@GetMapping("/etl/download-process/data/{id}")
	ModelAndView downloadProcessData(@PathVariable Integer id) throws IOException {
		return new ModelAndView(
			"etl/download_process_data",
			new HashMap<String, Object>() {{
				put("downloadProcessData", allitebooksService.downloadProcessData(id));
			}}
		);
	}
}
