package com.github.bogdanovmn.translator.web.orm;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

//@XmlEnum
public enum SourceType {
	BOOK,
	BLOG,
	ARTICLE,
	FORUM;

//	public String value() {
//		return name();
//	}
//
//	public static SourceType fromValue(String v) {
//		return valueOf(v);
//	}
}
