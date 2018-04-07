package com.github.bogdanovmn.translator.web.orm;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(
	uniqueConstraints = {
		@UniqueConstraint(
			columnNames = {"user_id", "word_id"}
		)
	}
)
public class UserRememberedWord extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "word_id")
	private Word word;

	@Column(nullable = false)
	private Date updated = new Date();

	public UserRememberedWord() {
	}

	public User getUser() {
		return user;
	}

	public UserRememberedWord setUser(User user) {
		this.user = user;
		return this;
	}

	public Word getWord() {
		return word;
	}

	public UserRememberedWord setWord(Word word) {
		this.word = word;
		return this;
	}

	public Date getUpdated() {
		return updated;
	}

	public UserRememberedWord setUpdated(Date updated) {
		this.updated = updated;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserRememberedWord that = (UserRememberedWord) o;

		if (!user.getId().equals(that.user.getId())) return false;
		return word.getId().equals(that.word.getId());
	}

	@Override
	public int hashCode() {
		int result = user.getId().hashCode();
		result = 31 * result + word.getId().hashCode();
		return result;
	}
}
