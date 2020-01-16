package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.common.spring.menu.MenuConfiguration;
import com.github.bogdanovmn.translator.core.definition.WordDefinitionService;
import com.github.bogdanovmn.translator.service.oxforddictionaries.OxfordWordDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
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
@EnableScheduling
@EnableConfigurationProperties(MenuConfiguration.class)
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public WordDefinitionService getWordDefinitionService() {
		return new OxfordWordDefinition();
	}
}

