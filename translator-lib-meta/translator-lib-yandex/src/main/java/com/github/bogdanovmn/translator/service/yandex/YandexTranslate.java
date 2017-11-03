package com.github.bogdanovmn.translator.service.yandex;

import com.github.bogdanovmn.httpclient.simple.SimpleHttpClient;
import com.github.bogdanovmn.translator.core.HttpTranslateService;
import com.github.bogdanovmn.translator.core.TranslateServiceException;
import com.github.bogdanovmn.translator.core.TranslateServiceParserException;
import com.github.bogdanovmn.translator.core.TranslateServiceUnavailableException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class YandexTranslate extends HttpTranslateService {
	public YandexTranslate() {
		super(
			new SimpleHttpClient(
				"https://translate.yandex.net/api/v1/tr.json/translate?id=0ba9ce9a.59c6161b.f4e73d1c-0-0&srv=tr-text&lang=en-ru&reason=paste&exp=1&text="
			)
		);
	}

	/*
	 {
		 code: 200,
		 lang: "en-ru",
		 text: [
		 	"особенность"
		 ]
	 }
 	 */
	@Override
	protected Set<String> parseServiceRawAnswer(String jsonText)
		throws TranslateServiceException
	{
		Set<String> result = null;

		try {
			JsonObject json = new JsonParser()
				.parse(jsonText).getAsJsonObject();

			int code = json.get("code").getAsInt();
			if (code == 200) {
				result = new HashSet<String>() {
					{
						add(
							json.get("text").getAsJsonArray()
								.get(0).getAsString()
						);
				}};
			}
			else {
				throw new TranslateServiceUnavailableException(
					String.format(
						"bad response code: %d", code
					)
				);
			}
		}
		catch (RuntimeException e) {
			throw new TranslateServiceParserException(
				String.format(
					"Parse json error: %s\nJSON: %s",
						e.getMessage(), jsonText
				)
			);
		}

		return result;
	}
}
