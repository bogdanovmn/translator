package com.github.bogdanovmn.translator.web.orm.entity.common;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntityWithUniqueName extends BaseEntity {
	@Column(unique = true, nullable = false)
	private String name;

	public String getName() {
		return name;
	}

	public BaseEntityWithUniqueName setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public boolean equals(Object thatObj) {
		if (this == thatObj) {
			return true;
		}
		if (thatObj == null || getClass() != thatObj.getClass()) {
			return false;
		}
		if (this.getName() == null) {
			return false;
		}

		BaseEntityWithUniqueName that = (BaseEntityWithUniqueName) thatObj;

		return this.getName().equals(that.getName());
	}

	@Override
	public int hashCode() {
		return this.getName() == null
			? 0
			: this.getName().hashCode();
	}
}
