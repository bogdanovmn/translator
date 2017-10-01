package com.github.bogdanovmn.translator.web.app.controller;

import com.github.bogdanovmn.translator.web.app.config.security.TranslateSecurityService;
import com.github.bogdanovmn.translator.web.app.controller.common.FormErrors;
import com.github.bogdanovmn.translator.web.app.controller.domain.form.UserRegistrationForm;
import com.github.bogdanovmn.translator.web.app.service.RegistrationService;
import com.github.bogdanovmn.translator.web.orm.entity.app.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class Registration {
	private final RegistrationService registrationService;
	private final TranslateSecurityService securityService;

	@Autowired
	public Registration(RegistrationService registrationService, TranslateSecurityService securityService) {
		this.registrationService = registrationService;
		this.securityService = securityService;
	}

	@GetMapping("/registration")
	public ModelAndView registration(Model model) {
		model.addAttribute("userForm", new UserRegistrationForm());

		return new ModelAndView("registration");
	}

	@PostMapping("/registration")
	public ModelAndView registration(
		@Valid UserRegistrationForm userForm,
		BindingResult bindingResult,
		Model model
	) {
		FormErrors formErrors = new FormErrors(bindingResult);

		if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
			formErrors.add("passwordConfirm", "Пароль не совпадает");
		}
		else if (this.registrationService.isUserExists(userForm.getEmail())) {
			formErrors.addCustom("Пользователь с таким email уже существует");
		}
		else if (this.registrationService.isUserNameExists(userForm.getName())) {
			formErrors.addCustom("Пользователь с таким именем уже существует");
		}

		if (formErrors.isNotEmpty()) {
			model.addAllAttributes(formErrors.getModel());
			return new ModelAndView("registration", model.asMap());
		}

		User user = this.registrationService.addUser(userForm);

		this.securityService.login(
			user.getName(),
			user.getPasswordHash()
		);

		return new ModelAndView("redirect:/to-remember");
	}
}
