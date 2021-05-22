package com.github.bogdanovmn.translator.cli.definition;


import com.github.bogdanovmn.cmdline.CmdLineAppBuilder;
import com.github.bogdanovmn.translator.core.definition.HttpWordDefinitionService;
import com.github.bogdanovmn.translator.service.oxforddictionaries.OxfordWordDefinition;

public class App {
	public static void main(String[] args) throws Exception {
		new CmdLineAppBuilder(args)
			.withJarName("word-definition")
			.withDescription("Word Definition CLI")
			.withArg("word", "Word or short phrase for definition")
			.withEntryPoint(
				cmdLine -> {
					try (HttpWordDefinitionService definitionService = new OxfordWordDefinition())
					{
						System.out.println(
							String.format(
								"Definition: %s",
								definitionService.definitions(
									cmdLine.getOptionValue("w")
								)
							)
						);
					}
				}
			).build().run();
	}
}
