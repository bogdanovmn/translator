package com.github.bogdanovmn.translator.web.app.infrastructure;

import com.github.bogdanovmn.translator.web.app.infrastructure.config.mustache.Layout;
import com.samskivert.mustache.Mustache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;

public abstract class AbstractMinVisualController extends AbstractController {
	@Autowired
	private Mustache.Compiler compiler;

	@Value("${server.context-path:}")
	private String contextPath;

	@ModelAttribute("layout")
	public Mustache.Lambda layout(Map<String, Object> model) {
		return new Layout(compiler, "min", contextPath);
	}
}
