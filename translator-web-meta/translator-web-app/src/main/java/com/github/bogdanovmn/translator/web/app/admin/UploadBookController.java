package com.github.bogdanovmn.translator.web.app.admin;

import com.github.bogdanovmn.translator.core.TranslateServiceUploadDuplicateException;
import com.github.bogdanovmn.translator.web.app.infrastructure.AdminMenu;
import com.github.bogdanovmn.translator.web.orm.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashMap;


@Controller
class UploadBookController extends AbstractVisualAdminController {
	private final UploadBookService uploadBookService;

	@Autowired
	UploadBookController(UploadBookService uploadBookService) {
		this.uploadBookService = uploadBookService;
	}

	@Override
	protected AdminMenu.ITEM currentAdminMenuItem() {
		return AdminMenu.ITEM.UPLOAD_BOOK;
	}

	@PostMapping("/upload-book")
	String upload(
		@RequestParam("file") MultipartFile file,
   		RedirectAttributes redirectAttributes
	) {
		try {
			Source source = uploadBookService.upload(file);
			redirectAttributes.addFlashAttribute("msg", "OK!");
			redirectAttributes.addFlashAttribute("source", source);
		}
		catch (IOException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("customError", "Что-то пошло не так при загрузке файла");
		}
		catch (TranslateServiceUploadDuplicateException e) {
			redirectAttributes.addFlashAttribute("customError", e.getMessage());
		}

		return "redirect:/admin/upload-book";
	}

	@GetMapping("/upload-book")
	ModelAndView form(
		@RequestHeader(name = "referer", required = false) String referer)
	{
		return new ModelAndView(
			"upload_book",
			new HashMap<String, Object>() {{
				put("referer" , referer);
			}}
		);
	}
}
