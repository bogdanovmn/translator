package com.github.bogdanovmn.translator.web.app.controller.domain.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

public class UserRegistrationForm {
	@NotNull
	@Size(min=3, max=20)
	private String name;

	@NotNull
	@Size(min=1, max=32)
	private String password;

	@NotNull
	@Size(min=1, max=32)
	private String passwordConfirm;

	@Null
	@Size(min=3, max=32)
	private String hearthpwnName;

	@NotNull
	private String email;


	public String getName() {
		return name;
	}

	public UserRegistrationForm setName(String name) {
		this.name = name;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public UserRegistrationForm setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public UserRegistrationForm setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
		return this;
	}

	public String getHearthpwnName() {
		return hearthpwnName;
	}

	public UserRegistrationForm setHearthpwnName(String hearthpwnName) {
		this.hearthpwnName = hearthpwnName;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public UserRegistrationForm setEmail(String email) {
		this.email = email;
		return this;
	}
}
