package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.web.app.config.security.TranslateSecurityService;
import com.github.bogdanovmn.translator.web.orm.User;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractController {
	@Autowired
	private TranslateSecurityService securityService;

	User getUser() {
		return securityService.getLoggedInUser();
	}
}
