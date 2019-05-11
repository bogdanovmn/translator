package com.github.bogdanovmn.translator.core.text;

public class IrregularVerb {
	private final String baseForm;
	private final String pastForm;
	private final String particForm;
	private final String translate;

	public IrregularVerb(String baseForm, String pastForm, String particForm, String translate) {
		this.baseForm = baseForm;
		this.pastForm = pastForm;
		this.particForm = particForm;
		this.translate = translate;
	}

	@Override
	public String toString() {
		return String.format(
			"%s - %s - %s (%s)",
				baseForm, pastForm, particForm, translate
		);
	}

	public String baseForm() {
		return baseForm;
	}
}
