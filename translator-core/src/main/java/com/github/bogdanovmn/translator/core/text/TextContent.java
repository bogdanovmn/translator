package com.github.bogdanovmn.translator.core.text;

import java.util.Set;

interface TextContent {

	Set<String> uniqueWords();

	Set<String> normalizedWords();

	int wordFrequency(String word);
}
