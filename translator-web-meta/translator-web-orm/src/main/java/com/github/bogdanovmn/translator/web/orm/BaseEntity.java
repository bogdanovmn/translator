package com.github.bogdanovmn.translator.web.orm;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@MappedSuperclass
public abstract class BaseEntity {
	@Id
	@GeneratedValue
	@Access(AccessType.PROPERTY)
	protected Integer id;

	@Transient
	protected Integer ref;

	public BaseEntity(Integer id) {
		this.id = id;
	}

	public BaseEntity() {}

	@XmlTransient
//	@XmlID
//	@XmlJavaTypeAdapter(ExportToXmlIdAdapter.class)
//	@XmlAttribute(name = "ref")
	public Integer getId() {
		return id;
	}

	public BaseEntity setId(Integer id) {
		this.id = id;
		this.ref = id;
		return this;
	}

	@XmlID
	@XmlAttribute(name = "ref")
	@XmlJavaTypeAdapter(ExportToXmlIdAdapter.class)
	public Integer getRef() {
		return this.ref;
	}

	public BaseEntity setRef(Integer ref) {
		this.ref = ref;
		return this;
	}
}
