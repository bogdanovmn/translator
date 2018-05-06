package com.github.bogdanovmn.translator.web.app.toremember;

import com.github.bogdanovmn.translator.core.TranslateServiceException;
import com.github.bogdanovmn.translator.core.TranslateServiceUnknownWordException;
import com.github.bogdanovmn.translator.web.app.AbstractController;
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
public class WordController extends AbstractController {
	private final ToRememberService toRememberService;

	@Autowired
	public WordController(ToRememberService toRememberService) {
		this.toRememberService = toRememberService;
	}

	@PutMapping("/remembered")
	public ResponseEntity remembered(@PathVariable Integer id) {
		this.toRememberService.rememberWord(id);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/translate")
	public ResponseEntity<String> translate(@PathVariable Integer id) {
		String result = null;
		try {
			result = this.toRememberService.translateWord(id);
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
	public Object holdOver(@PathVariable Integer id) {
		this.toRememberService.holdOverWord(id);
		return null;
	}
}
