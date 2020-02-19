package com.github.bogdanovmn.translator.web.app.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
class GitHubOauth2UserInfoResponse implements Oauth2UserInfoResponse {

	@JsonProperty("login")
	private final String externalId;

	@JsonProperty("name")
	private final String name;

	@JsonProperty("email")
	private final String email;

}
