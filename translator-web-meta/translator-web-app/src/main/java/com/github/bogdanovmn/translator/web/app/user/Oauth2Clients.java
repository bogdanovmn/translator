package com.github.bogdanovmn.translator.web.app.user;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@Component
class Oauth2Clients {
	private final Oauth2ClientConfiguration configuration;

	@Autowired
	public Oauth2Clients(Oauth2ClientConfiguration configuration) {
		this.configuration = configuration;
	}

	OAuth20Service service(OAuth2Provider provider) {
		Oauth2ClientConfiguration.Settings providerSettings = configuration.byProvider(provider);
		return new ServiceBuilder()
			.apiKey(providerSettings.getClientId())
			.apiSecret(providerSettings.getSecretKey())
			.callback(
				Optional.ofNullable(
					configuration.getCallbackUrlPrefix())
				.orElse(
					ServletUriComponentsBuilder.fromCurrentContextPath().build().toString()
				)
				+ configuration.getCallbackUrl()
				+ provider
			)
			.grantType("authorization_code")
			.build(provider.getApiInstance());
	}
}
