package com.github.bogdanovmn.translator.web.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController extends AbstractMinVisualController {
	@GetMapping("/login")
	public ModelAndView form(Model model, String error) {
		if (this.getUser() != null) {
			return new ModelAndView("redirect:/to-remember/all");
		}

		if (error != null) {
			model.addAttribute("customError", "Попробуйте еще разок");
		}

		return new ModelAndView("login", model.asMap());
	}
}
