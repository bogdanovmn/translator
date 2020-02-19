package com.github.bogdanovmn.translator.web.app.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
class UserRegistrationForm {
	@NotNull
	@Size(min=3, max=20)
	private String name;

	@NotNull
	@Size(min=1, max=32)
	private String password;

	@NotNull
	@Size(min=1, max=32)
	private String passwordConfirm;

	@NotNull
	private String email;
}
