package com.github.bogdanovmn.translator.etl.allitbooks;


import com.github.bogdanovmn.cmdlineapp.CmdLineAppBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner {
	private static final Logger LOG = LoggerFactory.getLogger(App.class);

	@Autowired
	private BookMetaImport bookMetaImport;

	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(App.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		new CmdLineAppBuilder(args)
			.withJarName("allitebooks-etl")
			.withDescription("allitebooks import CLI")
			.withEntryPoint(
				cmdLine -> {
					bookMetaImport.run();
				}
			).build().run();
	}
}
