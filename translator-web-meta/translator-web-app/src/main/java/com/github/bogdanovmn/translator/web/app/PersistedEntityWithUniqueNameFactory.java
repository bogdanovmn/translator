package com.github.bogdanovmn.translator.web.app;

import com.github.bogdanovmn.translator.web.orm.entity.common.BaseEntityWithUniqueName;
import com.github.bogdanovmn.translator.web.orm.repository.common.BaseEntityWithUniqueNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PersistedEntityWithUniqueNameFactory {
	private final Map<Class<?>, Map<String, BaseEntityWithUniqueName>> singleEntityCache = new ConcurrentHashMap<>();
	private final Repositories repositories;

	@Autowired
	public PersistedEntityWithUniqueNameFactory(WebApplicationContext context) {
		this.repositories = new Repositories(context);
	}


//	public BaseEntityWithUniqueName get(Class<? extends BaseEntityWithUniqueName> clazz, String name) {
	public <T extends BaseEntityWithUniqueName> T get(Class<T> clazz, String name) {
		T result;

		if (!this.singleEntityCache.containsKey(clazz)) {
			this.singleEntityCache.put(clazz, new HashMap<>());
		}

		if (!this.singleEntityCache.get(clazz).containsKey(name)) {
			BaseEntityWithUniqueNameRepository repository = (BaseEntityWithUniqueNameRepository) this.repositories.getRepositoryFor(clazz);
			result = (T) repository.findFirstByName(name);

			if (result != null) {
				this.singleEntityCache.get(clazz).put(name, result);
			}
			else {
				try {
					result = (T) clazz.newInstance().setName(name);
				}
				catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
				repository.save(result);
			}
		}
		else {
			result = (T) this.singleEntityCache.get(clazz).get(name);
		}

		return result;
	}
}