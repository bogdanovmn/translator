package com.github.bogdanovmn.translator.web.app.controller;

import com.github.bogdanovmn.translator.web.app.controller.domain.common.HeadMenu;
import com.github.bogdanovmn.translator.web.app.service.ToRememberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
@RequestMapping("/to-remember")
public class ToRemember extends BaseController {
	private final ToRememberService toRememberService;

	@Autowired
	public ToRemember(ToRememberService toRememberService) {
		this.toRememberService = toRememberService;
	}

	@GetMapping("/all")
	public ModelAndView listAll() {
		return new ModelAndView(
			"to_remember",
			new HashMap<String, Object>() {{
				put("menu"    , new HeadMenu(HeadMenu.HMI_TO_REMEMBER).getItems());
				put("userName", getUser().getName());
				put("words"   , toRememberService.getAll());
			}}
		);
	}
}
