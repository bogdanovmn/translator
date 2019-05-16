package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntityWithUniqueName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor

@Entity
public class ProperName extends BaseEntityWithUniqueName {
	@OneToMany(mappedBy = "properName")
	private List<ProperNameSource> properNameSources;

	public ProperName(String name) {
		super(name);
	}

	public ProperName(Integer id) {
		super(id);
	}
}
