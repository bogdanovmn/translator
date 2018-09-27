package com.github.bogdanovmn.translator.web.app.admin.etl;

import com.github.bogdanovmn.translator.web.app.infrastructure.AbstractVisualAdminController;
import com.github.bogdanovmn.translator.web.app.infrastructure.AdminMenu;
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
	protected AdminMenu.ITEM currentAdminMenuItem() {
		return AdminMenu.ITEM.ETL;
	}

	@GetMapping("/etl/download-process")
	ModelAndView downloadProcess() {
		return new ModelAndView(
			"etl/download_process",
			"brief",
			allitebooksService.downloadProcessBrief()
		);
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
