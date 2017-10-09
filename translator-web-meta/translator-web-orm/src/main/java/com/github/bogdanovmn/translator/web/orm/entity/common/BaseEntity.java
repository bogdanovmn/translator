package com.github.bogdanovmn.translator.web.orm.entity.common;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
	@Id
	@GeneratedValue
	@Access(AccessType.PROPERTY)
	protected Integer id;

	public BaseEntity(Integer id) {
		this.id = id;
	}

	public BaseEntity() {}

	public Integer getId() {
		return id;
	}

	public BaseEntity setId(Integer id) {
		this.id = id;
		return this;
	}
}
