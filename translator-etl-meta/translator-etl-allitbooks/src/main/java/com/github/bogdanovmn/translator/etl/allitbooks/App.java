package com.github.bogdanovmn.translator.etl.allitbooks;


import com.github.bogdanovmn.cmdlineapp.CmdLineAppBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class App implements CommandLineRunner {
	private final BookMetaImport bookMetaImport;
	private final DownloadService downloadService;

	@Autowired
	public App(BookMetaImport bookMetaImport, DownloadService downloadService) {
		this.bookMetaImport = bookMetaImport;
		this.downloadService = downloadService;
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(App.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		new CmdLineAppBuilder(args)
			.withJarName("allitebooks-import")
			.withDescription("allitebooks import CLI")
			.withEntryPoint(
				cmdLine -> {
					downloadService.download();
//					bookMetaImport.run();
				}
			).build().run();
	}
}
