package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor

@Entity
public class WordDefinitionServiceLog extends BaseEntity {
	@OneToOne
	@JoinColumn(name = "word_id", referencedColumnName = "id")
	private Word word;

	@Enumerated(EnumType.STRING)
	private Status status;

	private LocalDateTime updated;

	@Column(length = 2000)
	private String message;

	public WordDefinitionServiceLog setMessage(String msg) {
		message = msg;
		updated = LocalDateTime.now();
		return this;
	}

	public WordDefinitionServiceLog error(String msg) {
		status = Status.ERROR;
		setMessage(msg);
		return this;
	}

	public void done() {
		status = Status.DONE;
		updated = LocalDateTime.now();
	}

	public void notFound(String message) {
		status = Status.NOT_FOUND;
		setMessage(message);
	}

	public void anotherForm(String message) {
		status = Status.ANOTHER_FORM;
		setMessage(message);
	}

	public static enum Status {
		DONE, ERROR, NOT_FOUND, ANOTHER_FORM
	}
}
