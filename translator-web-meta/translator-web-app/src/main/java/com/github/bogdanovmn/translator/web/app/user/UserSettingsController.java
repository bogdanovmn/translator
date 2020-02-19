package com.github.bogdanovmn.translator.web.app.user;

import com.github.bogdanovmn.common.spring.menu.MenuItem;
import com.github.bogdanovmn.common.spring.mvc.Redirect;
import com.github.bogdanovmn.common.spring.mvc.ViewTemplate;
import com.github.bogdanovmn.translator.web.app.infrastructure.AbstractVisualController;
import com.github.bogdanovmn.translator.web.app.infrastructure.FormErrors;
import com.github.bogdanovmn.translator.web.app.infrastructure.config.security.Md5PasswordEncoder;
import com.github.bogdanovmn.translator.web.app.infrastructure.menu.MainMenuItem;
import com.github.bogdanovmn.translator.web.orm.entity.User;
import com.github.bogdanovmn.translator.web.orm.entity.UserOAuth2Repository;
import com.github.bogdanovmn.translator.web.orm.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/user/settings")
class UserSettingsController extends AbstractVisualController {
	private final UserRepository userRepository;
	private final UserOAuth2Repository userOAuth2Repository;

	@Autowired
	UserSettingsController(UserRepository userRepository, UserOAuth2Repository userOAuth2Repository) {
		this.userRepository = userRepository;
		this.userOAuth2Repository = userOAuth2Repository;
	}

	@Override
	protected MenuItem currentMenuItem() {
		return MainMenuItem.SETTINGS;
	}

	@InitBinder
	void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@GetMapping
	ModelAndView form(@RequestHeader(name = "referer", required = false) String referer) {
		User user = getUser();
		return new ViewTemplate("user_settings")
			.with("referer", referer)
			.with("userEmail", user.getEmail())
			.with("userRegistrationDate", user.getRegisterDate())
			.with("userSettingsForm", new UserSettingsForm())
			.with("socialProviders", userOAuth2Repository.findAllByUser(user))
		.modelAndView();
	}

	@PostMapping
	ModelAndView update(
		@Valid UserSettingsForm form,
		BindingResult bindingResult,
		Model model,
		@RequestHeader(name = "referer", required = false) String referer
	) {
		FormErrors formErrors = new FormErrors(bindingResult);

		User user = getUser();

		if (form.getNewPassword() != null) {
			String currentPassword = form.getCurrentPassword();
			if (currentPassword == null) {
				formErrors.add("currentPassword", "Необходимо указать");
			}
			else if (form.getNewPassword() == null) {
				formErrors.add("newPassword", "Необходимо указать");
			}
			else if (!form.getNewPassword().equals(form.getNewPasswordConfirm())) {
				formErrors.add("newPasswordConfirm", "Повтор нового пароля не совпадает");
			}
			else {
				Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
				if (!passwordEncoder.encode(currentPassword).equals(user.getPasswordHash())) {
					formErrors.add("currentPassword", "Пароль введен неправильно");
				}
				else {
					user.setPasswordHash(
						passwordEncoder.encode(
							form.getNewPassword()
						)
					);
				}
			}
		}

		if (formErrors.isNotEmpty()) {
			model.addAllAttributes(formErrors.getModel());
			model.addAttribute("referer", referer);
			return new ModelAndView("user_settings");
		}

		userRepository.save(user);

		return new Redirect("/unknown-words").modelAndView();
	}
}
