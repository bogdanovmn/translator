package com.github.bogdanovmn.translator.web.app.controller;

import com.github.bogdanovmn.translator.web.app.config.security.TranslateSecurityService;
import com.github.bogdanovmn.translator.web.orm.entity.app.User;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {
	@Autowired
	private TranslateSecurityService securityService;

	User getUser() {
		return securityService.getLoggedInUser();
	}
}
