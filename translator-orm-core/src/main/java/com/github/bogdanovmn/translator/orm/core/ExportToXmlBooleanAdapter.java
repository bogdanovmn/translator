package com.github.bogdanovmn.translator.orm.core;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ExportToXmlBooleanAdapter extends XmlAdapter<Boolean, Boolean> {
	@Override
	public Boolean unmarshal(Boolean v) throws Exception {
		return Boolean.TRUE.equals(v);
	}

	@Override
	public Boolean marshal(Boolean v) throws Exception {
		if (v) {
			return v;
		}
		return null;
	}
}