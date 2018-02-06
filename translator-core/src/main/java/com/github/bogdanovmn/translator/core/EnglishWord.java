package com.github.bogdanovmn.translator.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class EnglishWord {
	private final static List<String> POSTFIXES = Stream.of(
		"s", "es", "ing",
		"ed", "d",
		"er", "or",
		"ess", "less", "ful",
		"ment", "ness", "ian", "sion", "tion",
		"dom", "ship",
		"th",
		"able", "ible",
		"il", "al", "ly"
	).sorted(
		Comparator.comparing(String::length)
			.reversed()
	).collect(Collectors.toList());

	private final static List<String> PREFIXES = Stream.of(
		"re", "sub", "un", "super", "mega", "pre"
	).sorted(
		Comparator.comparing(String::length)
			.reversed()
	).collect(Collectors.toList());

	private final String value;

	EnglishWord(String value) {
		this.value = value;
	}

	boolean withPostfix() {
		for (String postfix : POSTFIXES) {
			if (this.value.endsWith(postfix)) {
				return true;
			}
		}
		return false;
	}

	boolean withPrefix() {
		for (String prefix : PREFIXES) {
			if (this.value.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}

	List<EnglishWord> posibleForms() {
		List<EnglishWord> result = new ArrayList<>();
		for (String prefix : PREFIXES) {
			result.add(
				new EnglishWord(prefix + this.value)
			);
			for (String postfix : POSTFIXES) {
				result.add(
					new EnglishWord(prefix + this.value + postfix)
				);
			}
		}
		for (String postfix : POSTFIXES) {
			result.add(
				new EnglishWord(this.value + postfix)
			);
		}

		return result;
	}

	EnglishWord base() {
		String result = this.value;

		List<String> prefixes = new ArrayList<>(PREFIXES);
		boolean changed;
		do {
			changed = false;
			for (int i = prefixes.size() - 1; i >= 0; i--) {
				if (result.length() < 5) {
					break;
				}
				String prefix = prefixes.get(i);
				if (result.startsWith(prefix)) {
					result = result.substring(prefix.length());
					changed = true;
					prefixes.remove(i);
				}
			}
		} while (changed && !prefixes.isEmpty());

		List<String> postfixes = new ArrayList<>(POSTFIXES);
		do {
			changed = false;
			for (int i = postfixes.size() - 1; i >= 0; i--) {
				if (result.length() < 5) {
					break;
				}
				String postfix = postfixes.get(i);
				if (result.endsWith(postfix)) {
					result = result.substring(0, result.length() - postfix.length());
					changed = true;
					postfixes.remove(i);
				}
			}
		} while (changed && !postfixes.isEmpty());

		return new EnglishWord(result);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		EnglishWord that = (EnglishWord) o;

		return value.equals(that.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public String toString() {
		return value;
	}
}
