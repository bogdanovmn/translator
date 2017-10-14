package com.github.bogdanovmn.translator.web.orm.factory;

import com.github.bogdanovmn.translator.web.orm.entity.app.UserRole;
import com.github.bogdanovmn.translator.web.orm.entity.common.BaseEntityWithUniqueName;
import com.github.bogdanovmn.translator.web.orm.entity.domain.TranslateProvider;
import com.github.bogdanovmn.translator.web.orm.entity.domain.Word;
import com.github.bogdanovmn.translator.web.orm.repository.app.UserRoleRepository;
import com.github.bogdanovmn.translator.web.orm.repository.common.BaseEntityWithUniqueNameRepository;
import com.github.bogdanovmn.translator.web.orm.repository.domain.TranslateProviderRepository;
import com.github.bogdanovmn.translator.web.orm.repository.domain.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class EntityRepositoryMapFactory {
	private Map<Class<? extends BaseEntityWithUniqueName>, BaseEntityWithUniqueNameRepository> map;

	@Autowired
	private WordRepository wordRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private TranslateProviderRepository translateProviderRepository;

	public EntityRepositoryMapFactory() {
	}

	private void init() {
		this.map = new HashMap<Class<? extends BaseEntityWithUniqueName>, BaseEntityWithUniqueNameRepository>()
		{{
			put(UserRole.class,          userRoleRepository);
			put(Word.class,              wordRepository);
			put(TranslateProvider.class, translateProviderRepository);
		}};
	}

	public BaseEntityWithUniqueNameRepository getRepository(Class<? extends BaseEntityWithUniqueName> aClass) {
		return this.map.get(aClass);
	}
}
