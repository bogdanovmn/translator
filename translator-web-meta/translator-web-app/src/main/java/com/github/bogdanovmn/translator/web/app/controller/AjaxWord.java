package com.github.bogdanovmn.translator.web.app.controller;

import com.github.bogdanovmn.translator.web.app.service.ToRememberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ajax/word/{id}")
public class AjaxWord extends BaseController {
	private final ToRememberService toRememberService;

	@Autowired
	public AjaxWord(ToRememberService toRememberService) {
		this.toRememberService = toRememberService;
	}

	@GetMapping("/remembered")
	public Object remembered(@PathVariable Integer id) {
		this.toRememberService.rememberWord(id);
		return null;
	}

	@GetMapping("/translate")
	public Object translate(@PathVariable Integer id) {
		this.toRememberService.translateWord(id);
		return null;
	}

	@GetMapping("/hold-over")
	public Object holdOver(@PathVariable Integer id) {
		this.toRememberService.holdOverWord(id);
		return null;
	}

	@GetMapping("/black-list")
	public Object blackList(@PathVariable Integer id) {
		this.toRememberService.blackListWord(id);
		return null;
	}

}
