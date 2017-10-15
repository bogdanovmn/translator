package com.github.bogdanovmn.translator.web.orm.entity.app;

import com.github.bogdanovmn.translator.web.orm.entity.common.BaseEntity;
import com.github.bogdanovmn.translator.web.orm.entity.domain.Word;

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
	private Date updated;

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
}
