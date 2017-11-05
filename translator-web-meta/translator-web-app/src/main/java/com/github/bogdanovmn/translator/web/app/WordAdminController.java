package com.github.bogdanovmn.translator.web.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/ajax/word/{id}")
public class WordAdminController extends AbstractController {
	private final ToRememberService toRememberService;

	@Autowired
	public WordAdminController(ToRememberService toRememberService) {
		this.toRememberService = toRememberService;
	}

	@PutMapping("/black-list")
	public ResponseEntity blackList(@PathVariable Integer id) {
		this.toRememberService.blackListWord(id);
		return ResponseEntity.ok().build();
	}

}
