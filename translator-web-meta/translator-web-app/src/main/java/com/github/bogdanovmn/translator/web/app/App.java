package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.core.TranslateService;
import com.github.bogdanovmn.translator.core.WordDefinitionService;
import com.github.bogdanovmn.translator.service.google.GoogleTranslate;
import com.github.bogdanovmn.translator.service.oxforddictionaries.OxfordWordDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = "com.github.bogdanovmn.translator.web")
@EnableJpaRepositories(
	basePackages = {
		"com.github.bogdanovmn.translator.web.orm",
		"com.github.bogdanovmn.translator.etl.allitbooks.orm"
	}
)
@EntityScan(
	basePackages = {
		"com.github.bogdanovmn.translator.web.orm",
		"com.github.bogdanovmn.translator.etl.allitbooks.orm"
	})
@EnableTransactionManagement
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public TranslateService getTranslateService() {
		return new GoogleTranslate();
	}

	@Bean
	public WordDefinitionService getWordDefinitionService() {
		return new OxfordWordDefinition();
	}
}

