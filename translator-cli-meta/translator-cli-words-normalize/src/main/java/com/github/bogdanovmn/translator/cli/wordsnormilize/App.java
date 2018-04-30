package com.github.bogdanovmn.translator.cli.wordsnormilize;


import com.github.bogdanovmn.cmdlineapp.CmdLineAppBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.github.bogdanovmn.translator.web.orm")
@EntityScan(basePackages = "com.github.bogdanovmn.translator.web.orm")
public class App implements CommandLineRunner {
	@Autowired
	private WordsNormalizeService wordsNormalizeService;

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
			.withJarName("words-normilize")
			.withDescription("Words normilize CLI")
			.withEntryPoint(
				cmdLine -> {
					wordsNormalizeService.normalize();
				}
			).build().run();
	}
}
