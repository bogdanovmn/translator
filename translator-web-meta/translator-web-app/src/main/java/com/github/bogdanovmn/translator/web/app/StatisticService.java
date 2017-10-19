package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.web.app.config.security.TranslateSecurityService;
import com.github.bogdanovmn.translator.web.orm.UserRememberedWordRepository;
import com.github.bogdanovmn.translator.web.orm.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticService {
	@Autowired
	private TranslateSecurityService securityService;
	@Autowired
	private UserRememberedWordRepository userRememberedWordRepository;
	@Autowired
	private WordRepository wordRepository;

	public Integer getUserWordRememberedPercent() {
		Integer rememberedCount = this.userRememberedWordRepository.countByUser(
			this.securityService.getLoggedInUser()
		);

		Integer totalWords = this.wordRepository.countByBlackListFalse();

		return totalWords == 0
			? 0
			: 100 * rememberedCount / totalWords;
	}
}
