package com.github.bogdanovmn.translator.web.app.admin.word.normalization;

import com.github.bogdanovmn.common.spring.menu.MenuItem;
import com.github.bogdanovmn.common.spring.mvc.ViewTemplate;
import com.github.bogdanovmn.translator.web.app.infrastructure.AbstractVisualAdminController;
import com.github.bogdanovmn.translator.web.app.infrastructure.menu.MainMenuItem;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
class NormalizedWordCandidateController extends AbstractVisualAdminController {

	private final WordsNormalizeService wordsNormalizeService;

	NormalizedWordCandidateController(WordsNormalizeService wordsNormalizeService) {
		this.wordsNormalizeService = wordsNormalizeService;
	}

	@Override
	protected MenuItem currentMenuItem() {
		return MainMenuItem.NORMALIZATION;
	}

	@GetMapping("/normalization")
	ModelAndView normalizationCandidates() {
		return new ViewTemplate("normalization/candidates")
			.with("entries"  , wordsNormalizeService.getAllCandidates())
		.modelAndView();
	}

	@PutMapping("/normalization/{id}")
	ResponseEntity apply(@PathVariable("id") Integer id) {
		wordsNormalizeService.approveCandidate(id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/normalization/{id}")
	ResponseEntity delete(@PathVariable("id") Integer id) {
		wordsNormalizeService.deleteCandidate(id);
		return ResponseEntity.ok().build();
	}
}
