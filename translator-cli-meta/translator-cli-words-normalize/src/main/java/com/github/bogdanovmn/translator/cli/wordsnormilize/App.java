package com.github.bogdanovmn.translator.cli.wordsnormilize;


import com.github.bogdanovmn.cmdlineapp.CmdLineAppBuilder;
import com.github.bogdanovmn.translator.web.app.admin.word.WordsNormalizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.github.bogdanovmn.translator.web.orm")
@EntityScan(basePackages = "com.github.bogdanovmn.translator.web.orm")
@ComponentScan(basePackages = "com.github.bogdanovmn.translator.web.app.admin.word")
@EnableTransactionManagement
public class App implements CommandLineRunner {
	private final WordsNormalizeService wordsNormalizeService;

	@Autowired
	public App(WordsNormalizeService wordsNormalizeService) {
		this.wordsNormalizeService = wordsNormalizeService;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(App.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	@Override
	public void run(String... args)
		throws Exception
	{
		new CmdLineAppBuilder(args)
			.withJarName("words-normalize")
			.withDescription("Words normalize CLI")
			.withEntryPoint(
				cmdLine -> {
					wordsNormalizeService.normalizeAll();
				}
			).build().run();
	}
}
