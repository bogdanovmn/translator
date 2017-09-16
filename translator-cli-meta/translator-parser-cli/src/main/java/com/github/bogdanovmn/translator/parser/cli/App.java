package com.github.bogdanovmn.translator.parser.cli;


import com.github.bogdanovmn.translator.core.EnglishText;
import com.github.bogdanovmn.translator.parser.pdf.PdfTextContent;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;

public class App {
	public static void main(String[] args) throws IOException {
		Options cliOptions = new Options();
		cliOptions
			.addOption(
				Option.builder("s")
					.longOpt("source")
					.hasArg().argName("SOURCE")
					.desc("text source (PDF file)")
					.required()
				.build()
			);

		CommandLineParser cmdLineParser = new DefaultParser();
		try {
			CommandLine cmdLine = cmdLineParser.parse(cliOptions, args);
			String sourceFileName = cmdLine.getOptionValue("s");

			new EnglishText(
				new PdfTextContent(
					new File(sourceFileName)
				).getText()
			).printStatistic();
		}
		catch (ParseException e) {
			System.err.println(e.getMessage());
			printHelp(cliOptions);
		}
	}

	private static void printHelp(Options opts) {
		new HelpFormatter()
			.printHelp(
				"java -jar parser.jar",
				"Text parser cli",
				opts,
				"",
				true
			);
	}
}
