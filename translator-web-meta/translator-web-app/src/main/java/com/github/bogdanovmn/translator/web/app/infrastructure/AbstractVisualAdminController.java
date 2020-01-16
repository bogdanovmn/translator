package com.github.bogdanovmn.translator.web.app.infrastructure;

import com.github.bogdanovmn.common.spring.menu.MenuItem;
import com.github.bogdanovmn.translator.web.app.infrastructure.menu.MainMenuItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public abstract class AbstractVisualAdminController extends AbstractVisualController {
	protected MenuItem currentMenuItem() { return MainMenuItem.ADMIN; }
}
