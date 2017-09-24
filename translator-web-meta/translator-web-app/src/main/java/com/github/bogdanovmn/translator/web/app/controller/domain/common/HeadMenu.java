package com.github.bogdanovmn.translator.web.app.controller.domain.common;

import java.util.ArrayList;
import java.util.List;

public class HeadMenu {
	public static final String HMI_COLLECTION_IN = "collection_in";
	public static final String HMI_COLLECTION_OUT = "collection_out";
	public static final String HMI_COLLECTION_BOOSTER = "booster";

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
			this.items.add(new HeadMenuItem(HMI_COLLECTION_IN,      "/collection/in/" , "Моя коллекция"));
			this.items.add(new HeadMenuItem(HMI_COLLECTION_OUT,     "/collection/out/", "Пополнить коллекцию"));
			this.items.add(new HeadMenuItem(HMI_COLLECTION_BOOSTER, "/booster/"       , "Купить бустер"));
		}
		this.isPrepared = true;
	}
}
