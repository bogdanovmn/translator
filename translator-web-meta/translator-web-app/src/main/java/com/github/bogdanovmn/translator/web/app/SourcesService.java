package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.web.orm.Source;
import com.github.bogdanovmn.translator.web.orm.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class SourcesService {
	private final SourceRepository sourceRepository;

	@Autowired
	public SourcesService(SourceRepository sourceRepository) {
		this.sourceRepository = sourceRepository;
	}

	List<Source> getAll() {
		return this.sourceRepository.findAll();
	}
}
