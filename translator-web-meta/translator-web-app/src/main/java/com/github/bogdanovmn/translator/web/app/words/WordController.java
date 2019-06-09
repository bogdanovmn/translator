package com.github.bogdanovmn.translator.web.app.words;

import com.github.bogdanovmn.translator.web.app.infrastructure.AbstractController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j

@RestController
@RequestMapping("/words/{id}")
class WordController extends AbstractController {
	private final UnknownWordsService unknownWordsService;

	@Autowired
	WordController(UnknownWordsService unknownWordsService) {
		this.unknownWordsService = unknownWordsService;
	}

	@PutMapping("/remembered")
	ResponseEntity remembered(@PathVariable Integer id) {
		unknownWordsService.rememberWord(getUser(), id);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/hold-over")
	ResponseEntity holdOver(@PathVariable Integer id) {
		unknownWordsService.holdOverWord(getUser(), id);
		return ResponseEntity.ok().build();
	}
}
