package com.github.bogdanovmn.translator.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class EnglishWord {
	private static final Logger LOG = LoggerFactory.getLogger(EnglishWord.class);

	public final static int MIN_BASE_LENGTH = 4;
	private final static List<String> POSTFIXES = Stream.of(
		"s", "es", "ies", "ing",
		"ed", "d",
		"er", "or", "ar",
		"ess",
		"ment", "age", "ure", "dom", "sion", "tion", "ation", "ization",
		"hood", "ship",
		"ness", "less",
		"th",
		"ful",
		"ish",
		"able", "ible",
		"il", "al", "ly", "ally",
		"y",
		"fy", "ify",
		"ise", "ize",
		"ist", "ian",
		"est"
	).sorted(
		Comparator.comparing(String::length)
			.reversed()
	).collect(Collectors.toList());

	private final static List<String> PREFIXES = Stream.of(
		"re", "super", "mega",
		"mis", "dis", "in", "non", "il", "im", "ir",
		"sub", "over", "under",
		"en", "ex",
		"pre", "post"
	).sorted(
		Comparator.comparing(String::length)
			.reversed()
	).collect(Collectors.toList());

	private final String value;

	EnglishWord(String value) {
		this.value = value;
	}

	boolean withAnyPostfix() {
		for (String postfix : POSTFIXES) {
			if (this.withPostfix(postfix)) {
				return true;
			}
		}
		return false;
	}

	boolean withAnyPrefix() {
		for (String prefix : PREFIXES) {
			if (this.withPrefix(prefix)) {
				return true;
			}
		}
		return false;
	}

	private boolean withPostfix(String postfix) {
		return (this.value.length() - postfix.length() >= MIN_BASE_LENGTH)
			&& this.value.endsWith(postfix);
	}

	private boolean withPrefix(String prefix) {
		return (this.value.length() - prefix.length() >= MIN_BASE_LENGTH)
			&& this.value.startsWith(prefix);
	}

	List<EnglishWord> possibleForms() {
		if (this.value.length() < MIN_BASE_LENGTH) {
			return Collections.emptyList();
		}

		boolean endsWithE = this.value.endsWith("e");
		String valueWithoutE = this.value.substring(0, this.value.length() - 1);

		List<EnglishWord> result = new ArrayList<>(PREFIXES.size() + PREFIXES.size() * POSTFIXES.size() + POSTFIXES.size());
		for (String prefix : PREFIXES) {
			result.add(
				new EnglishWord(prefix + this.value)
			);
			for (String postfix : POSTFIXES) {
				result.add(
					new EnglishWord(
						prefix
							+ (endsWithE && postfix.matches("^[euioa].*") ? valueWithoutE : this.value)
							+ postfix
					)
				);
			}
		}
		for (String postfix : POSTFIXES) {
			result.add(
				new EnglishWord(
					(endsWithE && postfix.matches("^[euioa].*") ? valueWithoutE : this.value)
						+ postfix
				)
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
			for (String prefix : prefixes) {
				if (new EnglishWord(result).withPrefix(prefix)) {
					LOG.debug("form: '{}', cut '{}'", result, prefix);

					result = result.substring(prefix.length());
					changed = true;
					prefixes.remove(prefix);
					// Начинаем проходить префиксы с самого начала
					break;
				}
			}
		} while (changed && !prefixes.isEmpty());

		List<String> postfixes = new ArrayList<>(POSTFIXES);
		do {
			changed = false;
			for (String postfix : postfixes) {
				if (new EnglishWord(result).withPostfix(postfix)) {
					LOG.debug("form: '{}', cut '{}'", result, postfix);

					result = result.substring(0, result.length() - postfix.length());
					changed = true;
					postfixes.remove(postfix);
					break;
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
