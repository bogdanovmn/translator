package com.github.bogdanovmn.translator.web.app.user;

interface Oauth2UserInfoResponse {
	String getExternalId();

	String getEmail();

	String getName();
}
