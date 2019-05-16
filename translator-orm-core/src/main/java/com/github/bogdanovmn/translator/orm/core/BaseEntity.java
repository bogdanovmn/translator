package com.github.bogdanovmn.translator.orm.core;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Setter
@Getter
@NoArgsConstructor

@XmlAccessorType(XmlAccessType.FIELD)

@MappedSuperclass
public abstract class BaseEntity {
	@XmlID
	@XmlJavaTypeAdapter(ExportToXmlIdAdapter.class)
	@XmlAttribute(name = "ref")

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

}
