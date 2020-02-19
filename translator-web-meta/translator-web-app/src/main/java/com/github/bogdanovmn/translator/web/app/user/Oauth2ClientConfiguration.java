package com.github.bogdanovmn.translator.web.app.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "oauth2")
//@EnableEncryptableProperties
@Setter
public class Oauth2ClientConfiguration {
	private Map<OAuth2Provider, Settings> providers;
	@Getter
	private String callbackUrl;
	@Getter
	private String callbackUrlPrefix;

	@Data
	static class Settings {
		private String clientId;
		private String secretKey;
		private String userInfoUrl;
	}

	Settings byProvider(OAuth2Provider provider) {
		return providers.get(provider);
	}
}
