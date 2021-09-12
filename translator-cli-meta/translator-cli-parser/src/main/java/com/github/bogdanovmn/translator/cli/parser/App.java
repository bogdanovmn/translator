package com.github.bogdanovmn.translator.cli.parser;


import com.github.bogdanovmn.cmdline.CmdLineAppBuilder;
import com.github.bogdanovmn.translator.core.text.EnglishText;
import com.github.bogdanovmn.translator.core.text.NormalizedWords;
import com.github.bogdanovmn.txtparser.DocumentContent;

import java.io.File;
import java.util.Set;

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

					EnglishText text = EnglishText.fromText(
						content.text()
					);
					System.out.println(
						text.statistic()
					);

					Set<String> uniqueWords = text.uniqueWords();
					NormalizedWords normalizedWords = NormalizedWords.of(uniqueWords);
					content.printMeta();

					System.out.println(
						String.format(
							"Total uniq words: %d\nTotal normalized words: %d (%.0f%%)",
								uniqueWords.size(),
								normalizedWords.get().size(),
								100 * ((float) normalizedWords.get().size() / uniqueWords.size())
						)
					);
				}
			).build().run();
	}
}
