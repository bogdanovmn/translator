package com.github.bogdanovmn.translator.web.app.controller.domain.common;

public class FilterValuesItem {
	private final int id;
	private final String name;
	private boolean selected = false;

	public FilterValuesItem(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isSelected() {
		return selected;
	}

	public FilterValuesItem setSelected(boolean selected) {
		this.selected = selected;
		return this;
	}
}
