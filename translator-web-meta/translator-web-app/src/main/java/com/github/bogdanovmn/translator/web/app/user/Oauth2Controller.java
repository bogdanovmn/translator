package com.github.bogdanovmn.translator.web.app.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.bogdanovmn.common.spring.mvc.Redirect;
import com.github.bogdanovmn.common.spring.mvc.ViewTemplate;
import com.github.bogdanovmn.translator.web.app.infrastructure.AbstractMinVisualController;
import com.github.bogdanovmn.translator.web.app.infrastructure.config.security.TranslateSecurityService;
import com.github.bogdanovmn.translator.web.orm.entity.User;
import com.github.scribejava.core.model.*;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequestMapping("/oauth2")
class Oauth2Controller extends AbstractMinVisualController {

	private final Oauth2Clients oauth2Clients;
	private final RegistrationService registrationService;
	private final TranslateSecurityService securityService;

	Oauth2Controller(Oauth2Clients oauth2Clients, RegistrationService registrationService, TranslateSecurityService securityService) {
		this.oauth2Clients = oauth2Clients;
		this.registrationService = registrationService;
		this.securityService = securityService;
	}

	@GetMapping
	ModelAndView oauth2(@RequestParam("provider") OAuth2Provider provider) {
		return new Redirect(
			oauth2Clients.service(provider).getAuthorizationUrl()
		).modelAndView();
	}

	@GetMapping("/callback")
	ModelAndView oauth2(
		@RequestParam("provider") OAuth2Provider provider,
		@RequestParam("code") String code
	) throws JsonProcessingException {

		ServletUriComponentsBuilder.fromCurrentContextPath().removePathExtension();
		OAuth20Service service = oauth2Clients.service(provider);
		OAuth2AccessToken accessToken = service.getAccessToken(new Verifier(code));

		OAuthRequest request = new OAuthRequest(Verb.GET, provider.getUserInfoUrl(), service);
		service.signRequest(accessToken, request);
		Response response = request.send();

		if (response.isSuccessful()) {
			Oauth2UserInfoResponse userInfo = provider.parsedResponse(response.getBody());
			User userWithSocialConnection = registrationService.userBySocialId(provider.name(), userInfo.getExternalId());
			String renewalPassword = DigestUtils.md5DigestAsHex(
				(userInfo.getExternalId() + System.currentTimeMillis() + Math.random())
					.getBytes()
			);
			if (userWithSocialConnection != null) {
				registrationService.updateUserPassword(userWithSocialConnection, renewalPassword);
				securityService.login(userWithSocialConnection.getName(), renewalPassword);
			}
			else {
				securityService.login(
					registrationService.addUserBySocialId(userInfo, provider, renewalPassword).getName(),
					renewalPassword
				);
			}
			return new Redirect("/unknown-words")
				.modelAndView();
		}
		else {
			return new ViewTemplate("login")
				.with("customError", "OAuth 2.0 error")
				.modelAndView();
		}
	}
}
