package com.github.bogdanovmn.translator.web.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminActionController extends AbstractController {
	@Autowired
	private ExportService exportService;

	@ModelAttribute
	public void addControllerCommonAttributes(Model model) {
		model.addAttribute("menu", new HeadMenu(HeadMenu.ITEM.IMPORT).getItems());
	}

	@GetMapping("/export")
	public void export(HttpServletResponse response)
		throws IOException, JAXBException
	{
		response.setHeader("Content-Disposition", "attachment; filename=translator_export.xml");
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

		this.exportService.export(response.getOutputStream());
	}

	@PostMapping("/import")
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


	@GetMapping("/import")
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
