package com.github.bogdanovmn.translator.web.app.infrastructure.menu;

import com.github.bogdanovmn.common.spring.menu.MenuItem;

public enum MainMenuItem implements MenuItem {

	// Regular user items

	REMEMBERED,
	UNKNOWN_WORDS,
	SOURCES,
	SETTINGS,
	ADMIN,
	CLOUD,

	// Admin items

	UPLOAD_BOOK,
	EXPORT,
	IMPORT,
	ETL,
	DEFINITION_LOG,
	NORMALIZATION,

	// Not selected

	NONE;

	@Override
	public String note() {
		return null;
	}
}
