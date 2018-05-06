package com.github.bogdanovmn.translator.web.app.toremember;

import com.github.bogdanovmn.translator.web.app.AbstractVisualController;
import com.github.bogdanovmn.translator.web.app.HeadMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
@RequestMapping("/to-remember")
public class ToRememberController extends AbstractVisualController {
	private final ToRememberService toRememberService;

	@Autowired
	public ToRememberController(ToRememberService toRememberService) {
		this.toRememberService = toRememberService;
	}

	@Override
	protected HeadMenu.ITEM currentMenuItem() {
		return HeadMenu.ITEM.TO_REMEMBER;
	}

	@GetMapping("/all")
	public ModelAndView listAll() {
		return new ModelAndView(
			"to_remember",
			new HashMap<String, Object>() {{
				put("words", toRememberService.getAll());
			}}
		);
	}

	@GetMapping("/source/{id}")
	public ModelAndView source(@PathVariable Integer id) {
		return new ModelAndView(
			"to_remember",
			toRememberService.getAllBySource(id).toView()
		);
	}
}
