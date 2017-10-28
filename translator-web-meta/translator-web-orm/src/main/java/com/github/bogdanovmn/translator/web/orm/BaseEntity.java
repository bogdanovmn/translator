package com.github.bogdanovmn.translator.web.orm;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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

	@XmlID
	@XmlJavaTypeAdapter(ExportToXmlIdAdapter.class)
	@XmlAttribute(name = "ref")
	public Integer getId() {
		return id;
	}

	public BaseEntity setId(Integer id) {
		this.id = id;
		return this;
	}
}
