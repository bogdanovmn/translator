package com.github.bogdanovmn.translator.web.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/import")
public class ImportController extends AbstractVisualController {
	@Autowired
	private ExportService exportService;

	@Override
	protected HeadMenu.ITEM currentMenuItem() {
		return HeadMenu.ITEM.IMPORT;
	}

	@PostMapping
	public String importFromFile(
		@RequestParam("file") MultipartFile file,
		RedirectAttributes redirectAttributes
	) {
		try {
			Map<String, Object> exportResult = this.exportService.importFromFile(file.getInputStream());
			redirectAttributes.addFlashAttribute("msg", "OK!");
			redirectAttributes.addFlashAttribute("exportResult", exportResult);
		}
		catch (IOException | JAXBException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("customError", "Что-то пошло не так при загрузке файла");
		}

		return "redirect:/admin/import";
	}


	@GetMapping
	public ModelAndView form(
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
