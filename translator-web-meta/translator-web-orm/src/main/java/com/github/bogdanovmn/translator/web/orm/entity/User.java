package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntityWithUniqueName;
import com.github.bogdanovmn.common.spring.menu.UserAuthorization;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class User extends BaseEntityWithUniqueName implements UserAuthorization {
	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String passwordHash;

	@Column(nullable = false)
	private Date registerDate;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(
		name = "role2user",
		joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
	)
	private Set<UserRole> roles;

	@OneToMany(mappedBy = "user")
	private List<UserHoldOverWord> holdOverWords;

	@OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
	private List<UserRememberedWord> rememberedWords;

	public User(String name) {
		super(name);
	}

	@Override
	public String userName() {
		return getName();
	}

	@Override
	public boolean withRole(String role) {
		return roles.contains(new UserRole(role));
	}
}
