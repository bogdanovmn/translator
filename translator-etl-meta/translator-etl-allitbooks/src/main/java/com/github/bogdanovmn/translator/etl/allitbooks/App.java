package com.github.bogdanovmn.translator.etl.allitbooks;


import com.github.bogdanovmn.cmdlineapp.CmdLineAppBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootApplication
public class App {
	public static void main(String[] args) throws Exception {
		new CmdLineAppBuilder(args)
			.withJarName("allitebooks-etl")
			.withDescription("allitebooks import CLI")
			.withEntryPoint(
				cmdLine -> {
					ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);
					BookRepository bookRepository = ctx.getBean(BookRepository.class);

					Site site = new Site();
					BookIterator bookIterator = site.getBookIterator();
					while (bookIterator.hasNext()) {
						Book book = bookIterator.next();
						if (book != null) {
							System.out.println(book.getOriginalUrl());
							Book persistenBook = bookRepository.findBookByOriginalUrl(book.getOriginalUrl());
							if (persistenBook != null) {
								book.setId(persistenBook.getId());
							}
							try {
								bookRepository.save(book);
								System.out.println(book.getTitle());
							}
							catch (DataIntegrityViolationException e) {
								System.err.println("Dublicate url");
							}
						}
					}
					long totalBooks = bookRepository.count();
					System.out.println(
						String.format("Total books: %d", totalBooks)
					);
				}
			).build().run();
	}
}
