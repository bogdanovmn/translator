package com.github.bogdanovmn.translator.web.app.user;

interface ExternalUser {
	String id();
	OAuth2Provider provider();
	String email();
}
