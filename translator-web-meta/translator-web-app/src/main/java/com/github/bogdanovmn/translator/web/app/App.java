package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.core.TranslateService;
import com.github.bogdanovmn.translator.core.exception.TranslateServiceException;
import com.github.bogdanovmn.translator.service.google.GoogleTranslate;
import com.github.bogdanovmn.translator.web.orm.factory.EntityFactory;
import com.github.bogdanovmn.translator.web.orm.factory.EntityRepositoryMapFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.github.bogdanovmn.translator.web.orm.repository")
@EntityScan(basePackages = "com.github.bogdanovmn.translator.web.orm.entity")
public class App {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public EntityFactory getEntityFactory() {
		return new EntityFactory();
	}

	@Bean(initMethod = "init")
	public EntityRepositoryMapFactory getEntityMapFactory() {
		return new EntityRepositoryMapFactory();
	}

	@Bean
	public TranslateService getTranslateService() throws TranslateServiceException {
		return new GoogleTranslate();
	}
}

