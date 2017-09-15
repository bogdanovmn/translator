package com.github.bogdanovmn.translator.translate.cli;


import org.apache.commons.cli.*;

public class App {
	public static void main(String[] args) {
		Options cliOptions = new Options();
		cliOptions
			.addOption(
				Option.builder("x")
					.longOpt("xxx")
					.hasArg().argName("LIMIT")
					.desc("max memory (bytes) for first level cache")
					.required()
				.build()
			);

		CommandLineParser cmdLineParser = new DefaultParser();
		try {
			CommandLine cmdLine = cmdLineParser.parse(cliOptions, args);
		}
		catch (ParseException e) {
			System.err.println(e.getMessage());
			printHelp(cliOptions);
		}
	}

	private static void printHelp(Options opts) {
		new HelpFormatter()
			.printHelp(
				"app",
				"Translator CLI",
				opts,
				".",
				true
			);
	}
}
