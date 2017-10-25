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

//	@Transient
//	protected String ref;

	public BaseEntity(Integer id) {
		this.id = id;
	}

	public BaseEntity() {}

//	@XmlTransient
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

//	@XmlID
//	@XmlAttribute(name = "ref")
//	public String getRef() {
//		return String.valueOf(this.id);
//	}
//
//	public void setRef(String ref) {
//		this.id = Integer.valueOf(ref);
//	}
}
