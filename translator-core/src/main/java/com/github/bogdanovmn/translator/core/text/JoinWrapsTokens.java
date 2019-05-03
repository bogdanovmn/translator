package com.github.bogdanovmn.translator.core.text;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
class JoinWrapsTokens {

	public static Tokens of(Tokens tokens) {
		List<Token> result = new ArrayList<>();
		List<Token> allTokens = tokens.all();
		int tokensCount = allTokens.size();
		boolean ignoreNext = false;
		for (int i = 0; i < tokensCount; i++) {
			if (ignoreNext) {
				ignoreNext = false;
				continue;
			}
			Token currentToken = allTokens.get(i);
			if (i != (tokensCount - 1) && currentToken.toString().endsWith("-")
					&& currentToken.isWord()
					&& (!currentToken.isCapital() || allTokens.get(i + 1).isCapital())
			) {
				Token nextToken = allTokens.get(i + 1);
				if (nextToken.isWord()) {
					result.add(
						new Token(
							currentToken.toString().replaceFirst("-*$", "") + nextToken
						)
					);
					ignoreNext = true;
					LOG.debug("Join wrap: {} + {} = {}", currentToken, nextToken, result.get(result.size() - 1));
				}
			}
			else {
				result.add(currentToken);
			}
		}
		return new Tokens(result);
	}
}
