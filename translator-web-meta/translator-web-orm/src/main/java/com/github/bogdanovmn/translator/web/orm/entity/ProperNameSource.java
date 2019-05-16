package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor

@Entity
@Table(name = "proper_name2source")
public class ProperNameSource extends BaseEntity {
	private Integer count;

	@ManyToOne
	@JoinColumn(name = "proper_name_id")
	private ProperName properName;

	@ManyToOne
	@JoinColumn(name = "source_id")
	private Source source;

	public ProperNameSource incCount(Integer incValue) {
		count += incValue;
		return this;
	}
}
