package com.github.bogdanovmn.translator.web.orm;

import com.github.bogdanovmn.translator.orm.core.BaseEntityWithUniqueName;
import com.github.bogdanovmn.translator.orm.core.BaseEntityWithUniqueNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
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

	@PostConstruct
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
