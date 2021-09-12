package com.github.bogdanovmn.translator.web.app.user;

import com.github.bogdanovmn.common.spring.mvc.Redirect;
import com.github.bogdanovmn.common.spring.mvc.ViewTemplate;
import com.github.bogdanovmn.translator.web.app.infrastructure.AbstractMinVisualController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
class LoginController extends AbstractMinVisualController {
	@GetMapping("/login")
	ModelAndView form(Model model, String error) {
		if (getUser() != null) {
			return new Redirect("/unknown-words").modelAndView();
		}

		ViewTemplate template = new ViewTemplate("login");
		if (error != null) {
			template.with("customError", "Попробуйте еще разок");
		}

		return template.modelAndView();
	}
}
