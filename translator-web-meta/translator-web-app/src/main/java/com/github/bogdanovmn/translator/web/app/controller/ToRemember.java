package com.github.bogdanovmn.translator.web.app.controller;

import com.github.bogdanovmn.translator.web.app.service.ToRememberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/to-remember")
public class ToRemember extends BaseController {
	private final ToRememberService toRememberService;

	@Autowired
	public ToRemember(ToRememberService toRememberService) {
		this.toRememberService = toRememberService;
	}

	@GetMapping("/all")
	public ModelAndView listAll(Model model) {

		model.addAttribute(
			"words",
			toRememberService.getAll(
				this.getUser()
			)
		);

		return new ModelAndView("to_remember");
	}
}
