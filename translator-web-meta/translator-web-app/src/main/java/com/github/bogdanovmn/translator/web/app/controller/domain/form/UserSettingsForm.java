package com.github.bogdanovmn.translator.web.app.controller.domain.form;

import javax.validation.constraints.Size;

public class UserSettingsForm {
	@Size(min=1, max=32)
	private String currentPassword;

	@Size(min=1, max=32)
	private String newPassword;

	@Size(min=1, max=32)
	private String newPasswordConfirm;

	@Size(min=3, max=32)
	private String hearthpwnUserName;

	public String getNewPassword() {
		return newPassword;
	}

	public UserSettingsForm setNewPassword(String newPassword) {
		this.newPassword = newPassword;
		return this;
	}

	public String getHearthpwnUserName() {
		return hearthpwnUserName;
	}

	public UserSettingsForm setHearthpwnUserName(String hearthpwnUserName) {
		this.hearthpwnUserName = hearthpwnUserName;
		return this;
	}

	public String getNewPasswordConfirm() {
		return newPasswordConfirm;
	}

	public UserSettingsForm setNewPasswordConfirm(String newPasswordConfirm) {
		this.newPasswordConfirm = newPasswordConfirm;
		return this;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public UserSettingsForm setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
		return this;
	}
}
