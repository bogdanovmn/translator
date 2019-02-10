package com.github.bogdanovmn.translator.core.text;

import com.github.bogdanovmn.translator.core.BigString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class StatisticDiff {
	private final static Pattern WORD_PATTERN = Pattern.compile("^((?:W|I)\\s+\\[\\s*\\d+])\\s+(\\w+)$");
	private final static Pattern FORM_PATTERN = Pattern.compile("^(\\w+)\\s+-->\\s+(\\[.*)$");

	private final Map<String, String> sourceWord = new HashMap<>();
	private final Map<String, String> sourceForm = new HashMap<>();
	private final Map<String, String> targetWord = new HashMap<>();
	private final Map<String, String> targetForm = new HashMap<>();

	void addSource(List<String> lines) {
		add(sourceWord, sourceForm, lines);
	}

	void addTarget(List<String> lines) {
		add(targetWord, targetForm, lines);
	}

	private void add(Map<String, String> wordHolder, Map<String, String> formHolder, List<String> lines) {
		lines.forEach(line -> {
			Matcher wordMatcher = WORD_PATTERN.matcher(line);
			if (wordMatcher.matches()) {
				wordHolder.put(wordMatcher.group(2), wordMatcher.group(1));
			}
			else {
				Matcher formMatcher = FORM_PATTERN.matcher(line);
				if (formMatcher.matches()) {
					formHolder.put(formMatcher.group(1), formMatcher.group(2));
				}
			}
		});
	}

	void print() {
		System.out.println("\n----- Words Diffs -----\n");
		printDiff(sourceWord, targetWord);
		System.out.println("\n----- Forms Diffs -----\n");
		printDiff(sourceForm, targetForm);
	}

	private void printDiff(Map<String, String> source, Map<String, String> target) {
		BigString deleted = new BigString();
		source.forEach(
			(word, line) -> {
				String targetWordLine = target.get(word);
				if (targetWordLine != null) {
					if (!targetWordLine.equals(line)) {
						System.out.println(
							String.format("CHANGED %-15s\t%-10s ==> %-10s", word, line, targetWordLine)
						);
					}
				}
				else {
					deleted.addLine("DELETED %-15s\t%s", word, line);
				}
			}
		);

		target.forEach(
			(word, line) -> {
				if (!source.containsKey(word)) {
					System.out.println(
						String.format("ADDED %-15s\t%s", word, line)
					);
				}
			}
		);

		System.out.println(deleted.toString());
	}
}
