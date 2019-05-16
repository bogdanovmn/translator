package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.web.app.infrastructure.config.security.TranslateSecurityService;
import com.github.bogdanovmn.translator.web.orm.entity.UserRememberedWordRepository;
import com.github.bogdanovmn.translator.web.orm.entity.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticService {
	private final TranslateSecurityService securityService;
	private final UserRememberedWordRepository userRememberedWordRepository;
	private final WordRepository wordRepository;

	@Autowired
	public StatisticService(TranslateSecurityService securityService, UserRememberedWordRepository userRememberedWordRepository, WordRepository wordRepository) {
		this.securityService = securityService;
		this.userRememberedWordRepository = userRememberedWordRepository;
		this.wordRepository = wordRepository;
	}

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
