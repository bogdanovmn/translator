package com.github.bogdanovmn.translator.web.app.infrastructure;

import java.util.ArrayList;
import java.util.List;

public class HeadMenu {
	public enum ITEM {
		REMEMBERED,
		UNKNOWN_WORDS,
		SOURCES,
		SETTINGS,
		ADMIN,
		CLOUD
	}

	private final String current;
	private List<MenuItem> items;
	private boolean isPrepared = false;
	private final boolean isAdmin;

	HeadMenu(ITEM current) {
		this.current = current.name();
		this.isAdmin = false;
	}

	HeadMenu(ITEM current, boolean isAdmin) {
		this.current = current.name();
		this.isAdmin = isAdmin;
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
		if (!this.isPrepared) {
			items = new ArrayList<>();
//			items.add(new MenuItem(ITEM.REMEMBERED.name(), "/remembered", "Уже изучено"));
			items.add(new MenuItem(ITEM.UNKNOWN_WORDS.name(), "/unknown-words", "Изучить"));
			items.add(new MenuItem(ITEM.SOURCES.name(), "/sources", "Источники"));
			items.add(new MenuItem(ITEM.CLOUD.name(), "/cloud", "Облако слов"));
			if (this.isAdmin) {
				items.add(new MenuItem(ITEM.ADMIN.name(), "/admin/upload-book", "Админка"));
			}
			this.isPrepared = true;
		}
	}
}
