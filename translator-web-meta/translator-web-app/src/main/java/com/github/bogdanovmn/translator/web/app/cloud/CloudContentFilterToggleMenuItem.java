package com.github.bogdanovmn.translator.web.app.cloud;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

class CloudContentFilterToggleMenuItem {
	private final CloudContentFilterToggle toggle;
	private final Map<CloudContentFilterToggle, Boolean> toggleValue;

	CloudContentFilterToggleMenuItem(final CloudContentFilterToggle toggle, final Map<CloudContentFilterToggle, Boolean> toggleValue) {
		this.toggle = toggle;
		this.toggleValue = toggleValue;
	}

	String title() {
		return toggle.title();
	}

	boolean selected() {
		return toggleValue.get(toggle);
	}

	String filterParams() {
		return toggleValue.entrySet().stream()
			.sorted(
				Comparator.comparingInt(x -> x.getKey().ordinal())
			)
			.map(x ->
				String.format("%s=%s",
					x.getKey(),
					x.getKey() == toggle
				)
			)
			.collect(Collectors.joining("&"));
	}
}
