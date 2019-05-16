package com.github.bogdanovmn.translator.web.orm.common;

import com.github.bogdanovmn.common.spring.jpa.EntityRepositoryMapFactory;
import com.github.bogdanovmn.translator.web.orm.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.github.bogdanovmn.common.spring")
class RepositoryMapConfig {
	@Autowired
	private WordRepository wordRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private WordDefinitionPartOfSpeechRepository wordDefinitionPartOfSpeechRepository;
	@Autowired
	private ProperNameRepository properNameRepository;

	@Bean
	EntityRepositoryMapFactory entityRepositoryMapFactory() {
		return new EntityRepositoryMapFactory.Builder()
			.map(UserRole.class, userRoleRepository)
			.map(Word.class, wordRepository)
			.map(ProperName.class, properNameRepository)
			.map(WordDefinitionPartOfSpeech.class, wordDefinitionPartOfSpeechRepository)
		.build();
	}
}
