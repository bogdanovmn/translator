package com.github.bogdanovmn.translator.web.app.controller;

import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class Exception {

	@ExceptionHandler({
//		MaxUploadSizeExceededException.class,
		FileUploadBase.FileSizeLimitExceededException.class
	})
	public String maxUploadSize(FileUploadBase.FileSizeLimitExceededException e, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("customError", e.getCause());
		return "redirect:/upload-book";
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ModelAndView handleMaxUploadException(MaxUploadSizeExceededException e, HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("error");
	}
}