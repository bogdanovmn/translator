package com.github.bogdanovmn.translator.translate.cli;


import com.github.bogdanovmn.translator.GoogleTranslate;
import com.github.bogdanovmn.translator.core.TranslateService;
import com.github.bogdanovmn.translator.core.TranslateServiceException;
import org.apache.commons.cli.*;

import java.io.IOException;

public class App {
	public static void main(String[] args) throws TranslateServiceException, IOException {
		Options cliOptions = new Options();
		cliOptions
			.addOption(
				Option.builder("t")
					.longOpt("text")
					.hasArg().argName("STRING")
					.desc("Word or short phrase to translate")
					.required()
				.build()
			);

		CommandLineParser cmdLineParser = new DefaultParser();
		try (TranslateService translateService = new GoogleTranslate())
		{
			CommandLine cmdLine = cmdLineParser.parse(cliOptions, args);
			System.out.println(
				String.format(
					"Translate: %s",
					translateService.translate(
						cmdLine.getOptionValue("t")
					)
				)
			);
		}
		catch (ParseException e) {
			System.err.println(e.getMessage());
			printHelp(cliOptions);
		}
	}

	private static void printHelp(Options opts) {
		new HelpFormatter()
			.printHelp(
				"java -jar translate.jar",
				"Translator CLI",
				opts,
				"",
				true
			);
	}
}
