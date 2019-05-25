package com.github.bogdanovmn.translator.web.app.infrastructure;

import com.github.bogdanovmn.translator.web.app.infrastructure.menu.MenuItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public abstract class AbstractVisualAdminController extends AbstractVisualController {
	protected MenuItem currentMenuItem() { return MenuItem.ADMIN; }
}
