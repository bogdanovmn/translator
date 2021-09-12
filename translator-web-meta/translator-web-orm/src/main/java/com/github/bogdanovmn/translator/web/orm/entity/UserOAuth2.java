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
			columnNames = {"providerName", "externalId"}
		),
		@UniqueConstraint(
			columnNames = {"providerName", "user_id"}
		)
	}
)
public class UserOAuth2 extends BaseEntity {
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(nullable = false)
	private String providerName;

	@Column(nullable = false)
	private String externalId;

	private String externalName;

	@Column(nullable = false)
	private LocalDateTime updated;
}
