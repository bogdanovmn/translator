package com.github.bogdanovmn.translator.web.app.infrastructure;

import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

public class ViewTemplate {
	private final String templateName;
	private final Map<String, Object> params = new HashMap<>();

	public ViewTemplate(String templateName) {
		this.templateName = templateName;
	}

	public ViewTemplate with(String paramName, Object paramValue) {
		params.put(paramName, paramValue);
		return this;
	}

	public ModelAndView modelAndView() {
		return new ModelAndView(
			templateName, params
		);
	}
}
