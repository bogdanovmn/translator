package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.web.orm.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;

@RestController
@RequestMapping("/admin")
public class AdminActionController extends BaseController {
	@Autowired
	private ExportService exportService;
	@Autowired
	private SourceRepository sourceRepository;

	@GetMapping("/export")
	public void export(HttpServletResponse response)
		throws IOException, JAXBException
	{
		response.setHeader("Content-Disposition", "attachment; filename=translator_export.xml");
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

		this.exportService.export(response.getOutputStream());
	}

}
