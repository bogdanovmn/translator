package com.github.bogdanovmn.translator.cli.parser;


import com.github.bogdanovmn.cmdlineapp.CmdLineAppBuilder;
import com.github.bogdanovmn.translator.core.text.EnglishText;
import com.github.bogdanovmn.translator.core.text.NormalizedWords;
import com.github.bogdanovmn.translator.parser.common.DocumentContent;

import java.io.File;

public class App {
	public static void main(String[] args) throws Exception {
		new CmdLineAppBuilder(args)
			.withJarName("parser")
			.withDescription("Text parser CLI")
			.withArg("source", "Source file name (PDF)")
			.withEntryPoint(
				cmdLine -> {
					DocumentContent content = DocumentContent.fromFile(
						new File(
							cmdLine.getOptionValue("s")
						)
					);

					EnglishText text = new EnglishText(
						content.text()
					);
					text.printStatistic();

					NormalizedWords normalizedWords = new NormalizedWords(text.words());
					content.printMeta();

					System.out.println(
						String.format(
							"Total uniq words: %d\nTotal normalized words: %d (%.0f%%)",
								text.words().size(),
								normalizedWords.get().size(),
								100 * ((float) normalizedWords.get().size() / text.words().size())
						)
					);
				}
			).build().run();
	}
}
