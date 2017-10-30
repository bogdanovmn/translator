package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.web.app.config.mustache.Layout;
import com.samskivert.mustache.Mustache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;

public abstract class AbstractMinVisualController extends AbstractController {
	@Autowired
	private Mustache.Compiler compiler;

	@ModelAttribute("layout")
	public Mustache.Lambda layout(Map<String, Object> model) {
		return new Layout(compiler, "min");
	}
}
