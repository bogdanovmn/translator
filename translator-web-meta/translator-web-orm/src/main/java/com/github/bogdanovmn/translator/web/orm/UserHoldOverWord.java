package com.github.bogdanovmn.translator.web.orm;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(
	uniqueConstraints = {
		@UniqueConstraint(
			columnNames = {"user_id", "word_id"}
		)
	},
	indexes = {
		@Index(
			columnList = "updated"
		)
	}

)
public class UserHoldOverWord extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "word_id")
	private Word word;

	@Column(nullable = false)
	private Date updated = new Date();

	public UserHoldOverWord() {
	}

	public User getUser() {
		return user;
	}

	public UserHoldOverWord setUser(User user) {
		this.user = user;
		return this;
	}

	public Word getWord() {
		return word;
	}

	public UserHoldOverWord setWord(Word word) {
		this.word = word;
		return this;
	}

	public Date getUpdated() {
		return updated;
	}

	public UserHoldOverWord setUpdated(Date updated) {
		this.updated = updated;
		return this;
	}
}
