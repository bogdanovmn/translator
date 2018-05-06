package com.github.bogdanovmn.translator.web.app.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordAdminController extends AbstractAdminController {
	private final WordAdminService wordAdminService;

	@Autowired
	public WordAdminController(WordAdminService wordAdminService) {
		this.wordAdminService = wordAdminService;
	}

	@PutMapping("/word/{id}/black-list")
	public ResponseEntity blackList(@PathVariable Integer id) {
		this.wordAdminService.blackListWord(id);
		return ResponseEntity.ok().build();
	}

}
