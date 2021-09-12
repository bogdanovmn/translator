package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Setter
@Getter
@NoArgsConstructor

@Entity
public class Source extends BaseEntity {
	private static final List<Pattern> RAW_NAME_PATTERNS = Arrays.asList(
		Pattern.compile("^(.*)\\s+-\\s+((?:\\w+\\.)*\\w+)$"),
		Pattern.compile("^(.*)\\.(\\w{1,4})$")
	);

	@Enumerated(EnumType.STRING)
	private SourceType type;
	private String author;
	private String title;
	private String rawName;
	private int wordsCount;
	private int blackListCount;

	@Column(length = 32, unique = true)
	private String contentHash;

	@OneToMany(mappedBy = "source")
	private Set<WordSource> wordSources;

	public Source(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.toString();
	}

	@Override
	public String toString() {
		if (StringUtils.isEmpty(this.author) || StringUtils.isEmpty(this.title)) {
			return normalizedRawName();
		}
		else {
			return String.format("%s - %s", this.author, this.title);
		}
	}

	private String normalizedRawName() {
		String normalizedName = rawName.replaceAll("_", " ");
		for (Pattern pattern : RAW_NAME_PATTERNS) {
			Matcher matcher = pattern.matcher(normalizedName);
			if (matcher.matches()) {
				normalizedName = String.format("%s [%s]",
					matcher.group(1),
					matcher.group(2)
				);
			}
		}
		return normalizedName;
	}

	public void incBlackListCount() {
		blackListCount++;
	}

	public int getNonBlackListCount() {
		return wordsCount - blackListCount;
	}
}
