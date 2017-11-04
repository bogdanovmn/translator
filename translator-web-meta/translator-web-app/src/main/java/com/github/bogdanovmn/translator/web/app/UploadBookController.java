package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.core.TranslateServiceUploadDuplicateException;
import com.github.bogdanovmn.translator.web.orm.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashMap;


@Controller
@RequestMapping("/admin/upload-book")
public class UploadBookController extends AbstractVisualController {
	private final UploadBookService uploadBookService;

	@Autowired
	public UploadBookController(UploadBookService uploadBookService) {
		this.uploadBookService = uploadBookService;
	}

	@Override
	protected HeadMenu.ITEM currentMenuItem() {
		return HeadMenu.ITEM.UPLOAD_BOOK;
	}

	@PostMapping
	public String upload(
		@RequestParam("file") MultipartFile file,
   		RedirectAttributes redirectAttributes
	) {
		try {
			Source source = this.uploadBookService.upload(file);
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

	@GetMapping
	public ModelAndView form(
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
