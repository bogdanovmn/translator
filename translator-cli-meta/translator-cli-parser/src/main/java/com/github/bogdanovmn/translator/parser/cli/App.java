package com.github.bogdanovmn.translator.parser.cli;


import com.github.bogdanovmn.cmdlineapp.CmdLineAppBuilder;
import com.github.bogdanovmn.translator.core.EnglishText;
import com.github.bogdanovmn.translator.parser.pdf.PdfTextContent;

import java.io.File;

public class App {
	public static void main(String[] args) throws Exception {
		new CmdLineAppBuilder(args)
			.withJarName("parser")
			.withDescription("Text parser CLI")
			.withArg("source", "Source file name (PDF)")
			.withEntryPoint(
				cmdLine -> {
					new EnglishText(
						new PdfTextContent(
							new File(
								cmdLine.getOptionValue("s")
							)
						).getText()
					).printStatistic();
				}
			).build().run();
	}
}
