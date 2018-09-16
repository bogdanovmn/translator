package com.github.bogdanovmn.translator.web.app.admin.source;

import com.github.bogdanovmn.translator.web.app.infrastructure.AbstractAdminController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
class SourcesAdminController extends AbstractAdminController {
	private final SourcesAdminService sourcesService;

	@Autowired
	SourcesAdminController(SourcesAdminService sourcesService) {
		this.sourcesService = sourcesService;
	}

	@DeleteMapping("/sources/{id}")
	ResponseEntity delete(@PathVariable Integer id) {
		sourcesService.delete(id);
		return ResponseEntity.ok().build();
	}
}
