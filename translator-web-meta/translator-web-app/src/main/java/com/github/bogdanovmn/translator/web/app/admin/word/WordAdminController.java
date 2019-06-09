package com.github.bogdanovmn.translator.web.app.admin.word;

import com.github.bogdanovmn.translator.web.app.infrastructure.AbstractAdminController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class WordAdminController extends AbstractAdminController {
	private final WordAdminService wordAdminService;

	@Autowired
	WordAdminController(WordAdminService wordAdminService) {
		this.wordAdminService = wordAdminService;
	}

	@PutMapping("/words/{id}/black-list")
	ResponseEntity blackList(@PathVariable Integer id) {
		wordAdminService.blackListWord(id);
		return ResponseEntity.ok().build();
	}

}
