package com.github.bogdanovmn.translator.web.app.controller;

import com.github.bogdanovmn.translator.web.app.config.security.TranslateSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Login {
	private final TranslateSecurityService securityService;

	@Autowired
	public Login(TranslateSecurityService securityService) {
		this.securityService = securityService;
	}

	@GetMapping("/login")
	public ModelAndView form(Model model, String error) {
		if (this.securityService.isLogged()) {
			return new ModelAndView("redirect:/");
		}

		if (error != null) {
			model.addAttribute("customError", "Попробуйте еще разок");
		}

		return new ModelAndView("login", model.asMap());
	}
}
