package com.github.bogdanovmn.translator.web.orm;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class User extends BaseEntityWithUniqueName {
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
	private Set<UserHoldOverWord> holdOverWords;

	@OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
	private Set<UserRememberedWord> rememberedWords;

	public User() {}

	public User(String name) {
		super(name);
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public User setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
		return this;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public User setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
		return this;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public User setRoles(Set<UserRole> roles) {
		this.roles = roles;
		return this;
	}

	public Set<UserHoldOverWord> getHoldOverWords() {
		return holdOverWords;
	}

	public User setHoldOverWords(Set<UserHoldOverWord> holdOverWords) {
		this.holdOverWords = holdOverWords;
		return this;
	}

	public Set<UserRememberedWord> getRememberedWords() {
		return rememberedWords;
	}

	public User setRememberedWords(Set<UserRememberedWord> rememberedWords) {
		this.rememberedWords = rememberedWords;
		return this;
	}
}
