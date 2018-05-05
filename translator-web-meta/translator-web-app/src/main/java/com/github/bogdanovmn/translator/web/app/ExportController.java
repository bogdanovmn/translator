package com.github.bogdanovmn.translator.web.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;

@Controller
@RequestMapping("/admin/export")
public class ExportController extends AbstractController {
	private final ExportService exportService;

	@Autowired
	public ExportController(ExportService exportService) {
		this.exportService = exportService;
	}

	@GetMapping
	public void export(HttpServletResponse response)
		throws IOException, JAXBException
	{
		response.setHeader("Content-Disposition", "attachment; filename=translator_export.xml");
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

		this.exportService.export(response.getOutputStream());
	}
}
