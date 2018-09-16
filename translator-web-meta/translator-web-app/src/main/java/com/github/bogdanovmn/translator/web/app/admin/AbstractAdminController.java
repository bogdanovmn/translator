package com.github.bogdanovmn.translator.web.app.admin;

import com.github.bogdanovmn.translator.web.app.infrastructure.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public abstract class AbstractAdminController extends AbstractController {
}
