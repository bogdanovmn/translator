package com.github.bogdanovmn.translator.web.orm;

import com.github.bogdanovmn.translator.orm.core.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor

@Entity
public class Source extends BaseEntity {
	@XmlAttribute
	@Enumerated(EnumType.STRING)
	private SourceType type;
	@XmlAttribute
	private String author;
	@XmlAttribute
	private String title;
	@XmlAttribute
	private String rawName;
	@XmlAttribute
	private Integer wordsCount;

	@XmlAttribute
	@Column(length = 32, unique = true)
	private String contentHash;

	@XmlTransient
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
			return this.rawName;
		}
		else {
			return String.format("%s - %s", this.author, this.title);
		}
	}

}
