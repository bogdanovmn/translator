package com.github.bogdanovmn.translator.web.app.controller.domain.common;

import ru.bmn.web.hsdb.model.entity.common.DictionaryEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FilterValues {
	private final Iterable entities;
	private final Integer selectedId;

	public FilterValues(Iterable rawEntities, Integer selectedId) {
		this.entities = rawEntities;
		this.selectedId = selectedId;
	}

	public List<FilterValuesItem> getItems() {
		List<DictionaryEntity> dictEntities = new ArrayList<>();

		for (Object obj : this.entities) {
			dictEntities.add((DictionaryEntity) obj);
		}

		return dictEntities.stream()
			.sorted(
				Comparator.comparingInt(DictionaryEntity::getSortValue)
			)
			.map(
				x -> {
					FilterValuesItem item = new FilterValuesItem(x.getId(), x.getName());
					if (x.getId().equals(this.selectedId)) {
						item.setSelected(true);
					}
					return item;
				}
			)
			.collect(Collectors.toList());
	}
}
