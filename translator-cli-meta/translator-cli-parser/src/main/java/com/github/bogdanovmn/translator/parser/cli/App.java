package com.github.bogdanovmn.translator.parser.cli;


import com.github.bogdanovmn.cmdlineapp.CmdLineAppBuilder;
import com.github.bogdanovmn.translator.core.EnglishText;
import com.github.bogdanovmn.translator.parser.pdf.PdfContent;

import java.io.File;

public class App {
	public static void main(String[] args) throws Exception {
		new CmdLineAppBuilder(args)
			.withJarName("parser")
			.withDescription("Text parser CLI")
			.withArg("source", "Source file name (PDF)")
			.withEntryPoint(
				cmdLine -> {
					PdfContent content = new PdfContent(
						new File(
							cmdLine.getOptionValue("s")
						)
					);

					new EnglishText(
						content.getText()
					).printStatistic();

					content.printMeta();
				}
			).build().run();
	}
}
