package com.github.bogdanovmn.translator.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ObjCounter<KeyType> {
	private final Map<KeyType, Integer> counter = new HashMap<>();

	public void increment(KeyType obj) {
		counter.put(
			obj,
			counter.getOrDefault(obj, 0) + 1
		);
	}

	public Set<KeyType> keys() {
		return Collections.unmodifiableSet(
			counter.keySet()
		);
	}

	public int get(KeyType obj) {
		return counter.getOrDefault(obj, 0);
	}

	public void remove(KeyType obj) {
		counter.remove(obj);
	}
}
