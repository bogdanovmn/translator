package com.github.bogdanovmn.translator.web.orm.entity.common;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
	@Id
	@GeneratedValue
	@Access(AccessType.PROPERTY)
	private Integer id;

	public Integer getId() {
		return id;
	}

	public BaseEntity setId(Integer id) {
		this.id = id;
		return this;
	}
}
