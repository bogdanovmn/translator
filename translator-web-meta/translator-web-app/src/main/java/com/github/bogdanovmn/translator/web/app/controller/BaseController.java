package com.github.bogdanovmn.translator.web.app.controller;

import com.github.bogdanovmn.translator.web.app.config.security.TranslateSecurityService;
import com.github.bogdanovmn.translator.web.app.service.StatisticService;
import com.github.bogdanovmn.translator.web.orm.entity.app.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class BaseController {
	@Autowired
	private TranslateSecurityService securityService;
	@Autowired
	private StatisticService statisticService;

	@ModelAttribute
	public void addCOmmonAttributes(Model model) {
		model.addAttribute("rememberedWordsPercent", this.statisticService.getUserWordRememberedPercent());
		model.addAttribute("userName", this.getUser().getName());
	}

	User getUser() {
		return securityService.getLoggedInUser();
	}
}
