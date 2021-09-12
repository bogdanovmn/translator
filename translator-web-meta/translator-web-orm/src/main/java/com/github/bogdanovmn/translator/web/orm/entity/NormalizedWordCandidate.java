package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor

@Entity
public class NormalizedWordCandidate extends BaseEntity {
	private String base;
	private String forms;

	public List<Word> getFormsWords() {
		if (forms != null) {
			return Arrays.stream(forms.replaceAll("[\\[\\],]+", "").split(","))
				.map(Word::new)
				.collect(Collectors.toList());
		}
		return null;
	}
}
