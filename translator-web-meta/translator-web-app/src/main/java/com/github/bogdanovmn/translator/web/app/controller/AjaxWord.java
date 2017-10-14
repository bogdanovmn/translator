package com.github.bogdanovmn.translator.web.app.controller;

import com.github.bogdanovmn.translator.core.exception.TranslateServiceException;
import com.github.bogdanovmn.translator.core.exception.TranslateServiceUnknownWordException;
import com.github.bogdanovmn.translator.web.app.service.ToRememberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/ajax/word/{id}")
public class AjaxWord extends BaseController {
	private final ToRememberService toRememberService;

	@Autowired
	public AjaxWord(ToRememberService toRememberService) {
		this.toRememberService = toRememberService;
	}

	@GetMapping("/remembered")
	public ResponseEntity remembered(@PathVariable Integer id) {
		this.toRememberService.rememberWord(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/translate")
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

	@GetMapping("/hold-over")
	public Object holdOver(@PathVariable Integer id) {
		this.toRememberService.holdOverWord(id);
		return null;
	}

	@GetMapping("/black-list")
	public ResponseEntity blackList(@PathVariable Integer id) {
		this.toRememberService.blackListWord(id);
		return ResponseEntity.ok().build();
	}

}
