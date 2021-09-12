package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntityWithUniqueName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class UserRole extends BaseEntityWithUniqueName {
	@ManyToMany(mappedBy = "roles")
	private Set<User> users;

	public UserRole(String name) {
		super(name);
	}

}
