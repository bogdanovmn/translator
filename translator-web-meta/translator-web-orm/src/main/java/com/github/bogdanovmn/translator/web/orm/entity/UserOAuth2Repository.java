package com.github.bogdanovmn.translator.web.orm.entity;

import com.github.bogdanovmn.common.spring.jpa.BaseEntityRepository;

public interface UserOAuth2Repository extends BaseEntityRepository<UserOAuth2> {
	UserOAuth2 findFirstByExternalIdAndAndProviderName(String externalId, String providerName);
}
