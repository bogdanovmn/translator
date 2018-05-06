package com.github.bogdanovmn.translator.web.app.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;

@Controller
public class ExportController extends AbstractAdminController {
	private final ExportService exportService;

	@Autowired
	public ExportController(ExportService exportService) {
		this.exportService = exportService;
	}

	@GetMapping("/export")
	public void export(HttpServletResponse response)
		throws IOException, JAXBException
	{
		response.setHeader("Content-Disposition", "attachment; filename=translator_export.xml");
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

		this.exportService.export(response.getOutputStream());
	}
}
