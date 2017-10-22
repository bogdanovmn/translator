package com.github.bogdanovmn.translator.web.app;

import java.util.ArrayList;
import java.util.List;

class HeadMenu {
	public enum ITEM {
		REMEMBERED,
		TO_REMEMBER,
		UPLOAD_BOOK,
		EXPORT,
		IMPORT
	}
//	private static final String HMI_REMEMBERED  = "remembered";
//	private static final String HMI_TO_REMEMBER = "to_remember";
//	private static final String HMI_UPLOAD_BOOK = "upload-book";
//	private static final String HMI_EXPORT = "export";
//	private static final String HMI_IMPORT = "import";

	private final String current;
	private List<HeadMenuItem> items;
	private boolean isPrepared = false;
	
	HeadMenu(ITEM current) {
		this.current = current.name();
		
	}

	List<HeadMenuItem> getItems() {
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
			this.items = new ArrayList<>(5);
			this.items.add(new HeadMenuItem(ITEM.REMEMBERED.name(),  "/remembered"     , "Уже изучено"));
			this.items.add(new HeadMenuItem(ITEM.TO_REMEMBER.name(), "/to-remember/all", "Изучить"));
			this.items.add(new HeadMenuItem(ITEM.UPLOAD_BOOK.name(), "/upload-book"    , "Загрузить книгу"));
			this.items.add(new HeadMenuItem(ITEM.EXPORT.name(),      "/admin/export"   , "Экспорт"));
			this.items.add(new HeadMenuItem(ITEM.IMPORT.name(),      "/admin/import"   , "Импорт"));
		}
		this.isPrepared = true;
	}
}
