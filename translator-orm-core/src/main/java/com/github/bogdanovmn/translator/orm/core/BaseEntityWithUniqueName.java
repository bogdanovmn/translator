package com.github.bogdanovmn.translator.orm.core;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAttribute;

@Setter
@Getter

@MappedSuperclass
public abstract class BaseEntityWithUniqueName extends BaseEntity {
	@XmlAttribute
	@Column(unique = true, nullable = false)
	private String name;

	public BaseEntityWithUniqueName() {
		super();
	}

	public BaseEntityWithUniqueName(String name) {
		super();
		this.name = name;
	}

	public BaseEntityWithUniqueName(Integer id) {
		super(id);
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
