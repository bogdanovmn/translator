package com.github.bogdanovmn.translator.web.orm.entity.app;

import com.github.bogdanovmn.translator.web.orm.entity.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class UserRole extends BaseEntity {
	@ManyToMany(mappedBy = "roles")
	private Set<User> users;


	public Set<User> getUsers() {
		return users;
	}

	public UserRole setUsers(Set<User> users) {
		this.users = users;
		return this;
	}
}
