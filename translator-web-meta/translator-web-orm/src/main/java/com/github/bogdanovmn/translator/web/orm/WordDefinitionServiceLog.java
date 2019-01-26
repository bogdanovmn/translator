package com.github.bogdanovmn.translator.web.orm;

import com.github.bogdanovmn.translator.orm.core.BaseEntity;
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
	@JoinColumn(name = "word_id")
	private Word word;

	@Enumerated(EnumType.STRING)
	private Status status;

	private LocalDateTime updated;

	@Column(length = 2000)
	private String message;

	public WordDefinitionServiceLog setMessage(String msg) {
		message = msg;
		status = Status.ERROR;
		updated = LocalDateTime.now();
		return this;
	}

	public WordDefinitionServiceLog error(String msg) {
		message = msg;
		status = Status.ERROR;
		updated = LocalDateTime.now();
		return this;
	}

	public void done() {
		status = Status.DONE;
		updated = LocalDateTime.now();
	}

	public void notFound(String message) {
		status = Status.NOT_FOUND;
		updated = LocalDateTime.now();
		this.message = message;
	}

	public void anotherForm(String message) {
		status = Status.ANOTHER_FORM;
		updated = LocalDateTime.now();
		this.message = message;
	}

	public static enum Status {
		DONE, ERROR, NOT_FOUND, ANOTHER_FORM
	}
}
