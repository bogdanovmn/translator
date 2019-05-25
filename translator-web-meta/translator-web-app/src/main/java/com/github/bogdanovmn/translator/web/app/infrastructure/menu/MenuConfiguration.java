package com.github.bogdanovmn.translator.web.app.infrastructure.menu;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "menu")
@Data
public class MenuConfiguration {
	private List<Item> items;

	@Data
	public static class Item {
		private String id;
		private String title;
		private String url;
		private boolean admin;
		private List<Item> items;
	}
}
