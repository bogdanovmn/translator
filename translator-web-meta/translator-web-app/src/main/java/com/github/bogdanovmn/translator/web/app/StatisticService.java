package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.web.orm.entity.User;
import com.github.bogdanovmn.translator.web.orm.entity.UserRememberedWordRepository;
import com.github.bogdanovmn.translator.web.orm.entity.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticService {
	private final UserRememberedWordRepository userRememberedWordRepository;
	private final WordRepository wordRepository;

	@Autowired
	public StatisticService(UserRememberedWordRepository userRememberedWordRepository, WordRepository wordRepository) {
		this.userRememberedWordRepository = userRememberedWordRepository;
		this.wordRepository = wordRepository;
	}

	public Map<String, Integer> getUserWordRememberedStatistic(User user) {
		Map<String, Integer> result = new HashMap<>();

		Integer rememberedCount = userRememberedWordRepository.countByUserWithExistingSourcesOnly(user.getId());

		Integer totalWords = wordRepository.countByBlackListFalseAndSourcesCountGreaterThan(0);

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
