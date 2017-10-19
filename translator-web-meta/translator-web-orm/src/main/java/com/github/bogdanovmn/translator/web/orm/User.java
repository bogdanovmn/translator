package com.github.bogdanovmn.translator.web.orm;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@NamedNativeQuery(
	name = "User.getWordsToRemember",
	resultClass = Word.class,
	query =
		"select w.*"
		+ " from  word w"
		+ " left join user_hold_over_word uhow on w.id = uhow.word_id and uhow.user_id = ?1 "
		+ " left join user_remembered_word urw on w.id = urw.word_id and urw.user_id = ?1 "
		+ " where urw.word_id is NULL "
		+ " and   uhow.word_id is NULL "
		+ " and   w.black_list = 0 "
		+ " order by w.sources_count desc, w.frequence desc "
		+ " limit 5"
)
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

	@OneToMany(mappedBy = "user")
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
