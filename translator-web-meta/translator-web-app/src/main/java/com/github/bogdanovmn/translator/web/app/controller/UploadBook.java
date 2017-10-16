package com.github.bogdanovmn.translator.web.app.controller;

import com.github.bogdanovmn.translator.core.exception.TranslateServiceUploadDuplicateException;
import com.github.bogdanovmn.translator.web.app.controller.domain.common.HeadMenu;
import com.github.bogdanovmn.translator.web.app.service.UploadBookService;
import com.github.bogdanovmn.translator.web.orm.entity.domain.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashMap;


@Controller
@RequestMapping("/upload-book")
public class UploadBook extends BaseController {
	private final UploadBookService uploadBookService;

	@Autowired
	public UploadBook(UploadBookService uploadBookService) {
		this.uploadBookService = uploadBookService;
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

		return "redirect:/upload-book";
	}

	@GetMapping
	public ModelAndView form(
		@RequestHeader(name = "referer", required = false) String referer)
	{
		return new ModelAndView(
			"upload_book",
			new HashMap<String, Object>() {{
				put("menu"    , new HeadMenu(HeadMenu.HMI_UPLOAD_BOOK).getItems());
				put("userName", getUser().getName());
				put("referer" , referer);
			}}
		);
	}

	@GetMapping("/history")
	public ModelAndView history() {
		return new ModelAndView(
			"upload_book_history",
			new HashMap<String, Object>() {{
				put("menu"    , new HeadMenu(HeadMenu.HMI_UPLOAD_BOOK).getItems());
				put("userName", getUser().getName());
				put("history" , uploadBookService.getHistory());
			}}
		);
	}

	@ExceptionHandler({MaxUploadSizeExceededException.class, MultipartException.class})
	public String exception(Throwable e) {
		return "redirect:/upload-book-exception";
	}
}
