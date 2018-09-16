package com.github.bogdanovmn.translator.web.app.admin.export;

import com.github.bogdanovmn.translator.web.app.infrastructure.AbstractVisualAdminController;
import com.github.bogdanovmn.translator.web.app.infrastructure.AdminMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
class ImportController extends AbstractVisualAdminController {
	private final ImportService importService;

	@Autowired
	ImportController(ImportService importService) {
		this.importService = importService;
	}

	@Override
	protected AdminMenu.ITEM currentAdminMenuItem() {
		return AdminMenu.ITEM.IMPORT;
	}

	@PostMapping("/import")
	String importFromFile(
		@RequestParam("file") MultipartFile file,
		RedirectAttributes redirectAttributes
	) {
		try {
			Map<String, Object> exportResult = importService.fromFile(file.getInputStream());
			redirectAttributes.addFlashAttribute("msg", "OK!");
			redirectAttributes.addFlashAttribute("exportResult", exportResult);
		}
		catch (IOException | JAXBException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("customError", "Что-то пошло не так при загрузке файла");
		}

		return "redirect:/admin/import";
	}


	@GetMapping("/import")
	ModelAndView form(
		@RequestHeader(name = "referer", required = false) String referer
	) {
		return new ModelAndView(
			"import",
			new HashMap<String, Object>() {{
				put("referer" , referer);
			}}
		);
	}
}
