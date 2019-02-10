package com.github.bogdanovmn.translator.core.text;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.Patch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class NormalizeQualityTest {
	private final static boolean OVERWRITE = false;

	private String goldName;

	public NormalizeQualityTest(String goldName) {
		this.goldName = goldName;
	}

	@Parameterized.Parameters(name = "{0}")
	public static Collection<Object[]> params() {
		return Arrays.asList(
			new Object[][]{
				{"fiction1"},
				{"fiction2"},
				{"tech1"},
				{"tech2"}
			}
		);
	}
	@Test
	public void compareWithModel() throws Exception {
		final Path goldModelPath = Paths.get("src/test/resources/gold/" + goldName + ".model");
		final String sourceName = "/books/" + goldName + ".txt";

		EnglishText englishText = EnglishText.fromText(
			new String(
				Files.readAllBytes(
					Paths.get(
						getClass().getResource(sourceName).toURI()
					)
				),
				StandardCharsets.UTF_8
			)
		);

		String statisticText = englishText.statistic();

		if (OVERWRITE) {
			Files.write(
				goldModelPath,
				statisticText.getBytes()
			);
		}
		List<String> model = Files.readAllLines(goldModelPath);

		Patch<String> patch = DiffUtils.diff(model, Arrays.asList(statisticText.split("\n")));

		StatisticDiff diff = new StatisticDiff();
		patch.getDeltas()
			.forEach(
				delta -> {
					diff.addSource(delta.getSource().getLines());
					diff.addTarget(delta.getTarget().getLines());
//					System.out.println(
//						String.format("%n%n%s%n----%s---->%n%s",
//							String.join("\n", delta.getSource().getLines()),
//							delta.getType().name(),
//							String.join("\n", delta.getTarget().getLines())
//						)
//					);
				}
			);

		diff.print();

		assertEquals(
			0,
			patch.getDeltas().size()
		);
	}
}
