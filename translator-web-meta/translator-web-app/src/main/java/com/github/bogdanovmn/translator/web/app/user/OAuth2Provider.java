package com.github.bogdanovmn.translator.web.app.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.apis.GitHubApi;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
enum OAuth2Provider {
	GitHub("https://api.github.com/user", GitHubApi.instance(), GitHubOauth2UserInfoResponse.class);

	private static ObjectMapper MAPPER = new ObjectMapper();

	private final String userInfoUrl;
	private final GitHubApi apiInstance;
	private final Class<? extends Oauth2UserInfoResponse> responseClass;

	public Oauth2UserInfoResponse parsedResponse(String body) throws JsonProcessingException {
		return MAPPER.readValue(body, responseClass);
	}
}
