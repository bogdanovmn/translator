package com.github.bogdanovmn.translator.web.app.toremember;

import com.github.bogdanovmn.translator.core.translate.TranslateServiceException;
import com.github.bogdanovmn.translator.core.translate.TranslateServiceUnknownWordException;
import com.github.bogdanovmn.translator.web.app.infrastructure.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/word/{id}")
class WordController extends AbstractController {
	private final ToRememberService toRememberService;

	@Autowired
	WordController(ToRememberService toRememberService) {
		this.toRememberService = toRememberService;
	}

	@PutMapping("/remembered")
	ResponseEntity remembered(@PathVariable Integer id) {
		toRememberService.rememberWord(id);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/translate")
	ResponseEntity<String> translate(@PathVariable Integer id) {
		String result = null;
		try {
			result = toRememberService.translateWord(id);
		}
		catch (TranslateServiceUnknownWordException e) {
			return ResponseEntity.notFound().build();
		}
		catch (TranslateServiceException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body(e.getMessage());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(result);
	}

	@PutMapping("/hold-over")
	ResponseEntity holdOver(@PathVariable Integer id) {
		toRememberService.holdOverWord(id);
		return ResponseEntity.ok().build();
	}
}
