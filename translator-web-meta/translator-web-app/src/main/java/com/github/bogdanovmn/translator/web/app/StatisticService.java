package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.web.app.config.security.TranslateSecurityService;
import com.github.bogdanovmn.translator.web.orm.UserRememberedWordRepository;
import com.github.bogdanovmn.translator.web.orm.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticService {
	@Autowired
	private TranslateSecurityService securityService;
	@Autowired
	private UserRememberedWordRepository userRememberedWordRepository;
	@Autowired
	private WordRepository wordRepository;

	public Map<String, Integer> getUserWordRememberedStatistic() {
		Map<String, Integer> result = new HashMap<>();

		Integer rememberedCount = this.userRememberedWordRepository.countByUser(
			this.securityService.getLoggedInUser()
		);

		Integer totalWords = this.wordRepository.countByBlackListFalse();

		result.put("rememberedCount", rememberedCount);
		result.put("totalWords", totalWords);
		result.put("toRememberCount", totalWords - rememberedCount);
		result.put(
			"rememberedPercent",
			totalWords == 0
				? 0
				: 100 * rememberedCount / totalWords
		);

		return result;
	}
}
