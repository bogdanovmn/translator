package com.github.bogdanovmn.translator.web.app.user;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OAuth2ProviderTest {

	@Test
	public void parsedResponse() throws URISyntaxException, IOException {
		Oauth2UserInfoResponse response = OAuth2Provider.GitHub.parsedResponse(
			new String(
				Files.readAllBytes(
					Paths.get(
						getClass().getResource("/github.json").toURI()
					)
				)
			)
		);

		assertThat(
			response.getExternalId(),
			is("bogdanovmn")
		);

		assertThat(
			response.getName(),
			is("Mikhail N Bogdanov")
		);

		assertThat(
			response.getEmail(),
			CoreMatchers.nullValue()
		);

	}
}