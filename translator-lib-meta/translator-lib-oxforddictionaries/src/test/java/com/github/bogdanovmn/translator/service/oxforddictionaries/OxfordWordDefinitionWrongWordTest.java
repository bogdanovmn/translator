package com.github.bogdanovmn.translator.service.oxforddictionaries;

import com.github.bogdanovmn.httpclient.core.HttpClient;
import com.github.bogdanovmn.translator.core.definition.ResponseAnotherWordFormException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class OxfordWordDefinitionWrongWordTest {

	@Mock
	private HttpClient httpClient;

	@Test(expected = ResponseAnotherWordFormException.class)
	public void shouldFoundAnotherWordForm() throws Exception {
		String html = new String(
			Files.readAllBytes(
				Paths.get(
					OxfordWordDefinitionWrongWordTest.class.getResource("/definition--mans--wrong-word--html").toURI()
				)
			),
			StandardCharsets.UTF_8
		);

		Mockito.when(httpClient.get(Mockito.anyString()))
			.thenReturn(html);

		try {
			new OxfordWordDefinition(httpClient).definitions("mans");
		}
		catch (ResponseAnotherWordFormException e) {
			assertEquals("man", e.definition().word());
			assertNotNull(e.definition().instances());
			throw e;
		}
	}
}