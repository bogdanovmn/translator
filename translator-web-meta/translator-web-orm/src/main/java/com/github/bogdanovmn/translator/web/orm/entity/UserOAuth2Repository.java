package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntityRepository;

import java.util.List;

public interface UserOAuth2Repository extends BaseEntityRepository<UserOAuth2> {
	UserOAuth2 findFirstByExternalIdAndAndProviderName(String externalId, String providerName);

	List<UserOAuth2> findAllByUser(User user);
}
