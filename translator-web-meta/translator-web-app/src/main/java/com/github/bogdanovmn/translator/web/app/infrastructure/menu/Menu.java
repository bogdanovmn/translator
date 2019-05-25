package com.github.bogdanovmn.translator.web.app.infrastructure.menu;

import com.github.bogdanovmn.translator.web.orm.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;


public class Menu {
	@Getter
	private List<Item> items;

	private Menu(List<Item> items) {
		this.items = items;
	}

	static Menu fromConfiguration(MenuConfiguration configuration, MenuItem selectedItem, User user) {
		boolean admin = user != null && user.getRoles().stream().anyMatch(x -> x.getName().equals("Admin"));
		List<Item> items = prepareItems(configuration.getItems(), selectedItem, admin);
		return new Menu(items);
	}

	private static List<Item> prepareItems(List<MenuConfiguration.Item> configItems, MenuItem selectedItem, boolean admin) {
		if (configItems == null || configItems.isEmpty()) {
			return null;
		}
		List<Item> items = new ArrayList<>();
		configItems.stream()
			.filter(item -> admin || item.isAdmin())
			.forEach(item
				-> items.add(
					Item.builder()
						.id(item.getId())
						.title(item.getTitle())
						.url(item.getUrl())
						.selected(item.getId().equals(selectedItem.name()))
						.subMenu(item.getItems() != null)
						.items(
							prepareItems(item.getItems(), selectedItem, admin)
						)
					.build()
				)
			);
		return items;
	}
	@Value
	@Builder
	public static class Item {
		private final String id;
		private final String title;
		private final String url;
		private final boolean selected;
		private final boolean subMenu;
		private final List<Item> items;
	}
}
