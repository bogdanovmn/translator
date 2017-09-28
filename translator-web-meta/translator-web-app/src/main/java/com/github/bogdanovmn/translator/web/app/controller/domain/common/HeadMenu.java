package com.github.bogdanovmn.translator.web.app.controller.domain.common;

import java.util.ArrayList;
import java.util.List;

public class HeadMenu {
	public static final String HMI_REMEMBERED  = "remembered";
	public static final String HMI_TO_REMEMBER = "to_remember";
	public static final String HMI_UPLOAD_BOOK = "upload-book";

	private final String current;
	private List<HeadMenuItem> items;
	private boolean isPrepared = false;
	
	public HeadMenu(String current) {
		this.current = current;
		
	}

	public List<HeadMenuItem> getItems() {
		this.prepare();

		for (HeadMenuItem headMenuItem : this.items) {
			if (headMenuItem.is(this.current)) {
				headMenuItem.select();
			}
			
		}
		return this.items;
	}
	
	private void prepare() {
		if (!this.isPrepared) {
			this.items = new ArrayList<>(3);
			this.items.add(new HeadMenuItem(HMI_REMEMBERED , "/remembered/" , "Уже изучено"));
			this.items.add(new HeadMenuItem(HMI_TO_REMEMBER, "/to-remember/", "Изучить"));
			this.items.add(new HeadMenuItem(HMI_UPLOAD_BOOK, "/upload-book/", "Загрузить книгу"));
		}
		this.isPrepared = true;
	}
}
