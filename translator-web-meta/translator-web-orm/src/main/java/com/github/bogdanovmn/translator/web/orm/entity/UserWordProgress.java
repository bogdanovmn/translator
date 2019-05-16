package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

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
public class UserWordProgress extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "word_id")
	private Word word;

	@Column(nullable = false)
	private LocalDateTime updated = LocalDateTime.now();

	private int holdOverCount = 0;

	public void incHoldOverCount() {
		holdOverCount++;
	}
}
