package com.github.bogdanovmn.translator.translate.cli;


import com.github.bogdanovmn.cmdlineapp.CmdLineAppBuilder;
import com.github.bogdanovmn.translator.core.TranslateService;
import com.github.bogdanovmn.translator.service.yandex.YandexTranslate;

public class App {
	public static void main(String[] args) throws Exception {
		new CmdLineAppBuilder(args)
			.withJarName("translate")
			.withDescription("Translator CLI")
			.withArg("text", "Word or short phrase to translate")
			.withEntryPoint(
				cmdLine -> {
					try (TranslateService translateService = new YandexTranslate())
					{
						System.out.println(
							String.format(
								"Translate: %s",
								translateService.translate(
									cmdLine.getOptionValue("t")
								)
							)
						);
					}
				}
			).build().run();
	}
}
