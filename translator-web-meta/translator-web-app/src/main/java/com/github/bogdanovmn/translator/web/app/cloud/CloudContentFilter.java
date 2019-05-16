package com.github.bogdanovmn.translator.web.app.cloud;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class CloudContentFilter {
	private final Map<CloudContentFilterToggle, Boolean> toggleValue;
	private final String resourcePath;

	CloudContentFilter(String resourcePath, Map<CloudContentFilterToggle, Boolean> toggleValue) {
		this.toggleValue = toggleValue;
		this.resourcePath = resourcePath;
	}

	List<CloudContentFilterToggleMenuItem> getMenu() {
		return Arrays.stream(CloudContentFilterToggle.values())
			.map(toggle -> new CloudContentFilterToggleMenuItem(toggle, toggleValue))
			.collect(Collectors.toList());
	}

	private CloudContentFilterToggle activeToggle() {
		return Arrays.stream(CloudContentFilterToggle.values())
			.filter(toggleValue::get)
			.findFirst()
			.orElse(CloudContentFilterToggle.ALL);
	}

	CloudWordsRepository cloudWordsRepository() {
		return activeToggle().repository();
	}
}
