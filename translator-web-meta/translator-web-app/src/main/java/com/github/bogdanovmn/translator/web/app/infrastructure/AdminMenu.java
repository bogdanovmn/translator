package com.github.bogdanovmn.translator.web.app.infrastructure;

import java.util.ArrayList;
import java.util.List;

public class AdminMenu {
	public enum ITEM {
		UPLOAD_BOOK,
		EXPORT,
		IMPORT,
		ETL,
		DEFINITION_LOG,
		NONE
	}

	private final String current;
	private List<MenuItem> items;
	private boolean isPrepared = false;

	AdminMenu(ITEM current) {
		this.current = current.name();
	}

	List<MenuItem> getItems() {
		prepare();

		for (MenuItem menuItem : items) {
			if (menuItem.is(current)) {
				menuItem.select();
			}
			
		}
		return items;
	}
	
	private void prepare() {
		if (!isPrepared) {
			items = new ArrayList<>();
			items.add(new MenuItem(ITEM.UPLOAD_BOOK.name(), "/admin/upload-book", "Загрузить книгу"));
			items.add(new MenuItem(ITEM.EXPORT.name(), "/admin/export", "Экспорт"));
			items.add(new MenuItem(ITEM.IMPORT.name(), "/admin/import", "Импорт"));
			items.add(new MenuItem(ITEM.ETL.name(), "/admin/etl/download-process", "allitebooks"));
			items.add(new MenuItem(ITEM.ETL.name(), "/admin/definitions/log/last", "definitions"));
		}
		isPrepared = true;
	}
}
