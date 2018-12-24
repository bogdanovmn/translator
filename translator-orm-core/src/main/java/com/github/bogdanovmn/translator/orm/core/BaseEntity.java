package com.github.bogdanovmn.translator.orm.core;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@NoArgsConstructor

@MappedSuperclass
public abstract class BaseEntity {
	@Id
	@GeneratedValue(
		strategy= GenerationType.AUTO,
		generator="native"
	)
	@GenericGenerator(
		name = "native",
		strategy = "native"
	)
	@Access(AccessType.PROPERTY)
	protected Integer id;

	public BaseEntity(Integer id) {
		this.id = id;
	}

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
