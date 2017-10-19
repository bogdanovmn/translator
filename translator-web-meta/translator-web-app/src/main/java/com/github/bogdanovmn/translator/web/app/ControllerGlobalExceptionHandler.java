package com.github.bogdanovmn.translator.web.app;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ControllerGlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(MultipartException.class)
	public String handleMultipartException(MultipartException exception, RedirectAttributes attributes){
		attributes.addFlashAttribute("customError", exception.getCause().getMessage());
		return "redirect:/upload-book";
	}
}
