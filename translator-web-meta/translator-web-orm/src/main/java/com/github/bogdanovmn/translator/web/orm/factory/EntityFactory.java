package com.github.bogdanovmn.translator.web.orm.factory;

import com.github.bogdanovmn.translator.web.orm.entity.common.BaseEntityWithUniqueName;
import com.github.bogdanovmn.translator.web.orm.repository.common.BaseEntityWithUniqueNameRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityFactory {
	private final Map<Class<?>, Map<String, BaseEntityWithUniqueName>> singleEntityCache = new ConcurrentHashMap<>();
	private final Map<Class<?>, Iterable> setEntityCache = new ConcurrentHashMap<>();

	@Autowired
	private EntityMapFactory entityMapFactory;

	public EntityFactory() {}


	public BaseEntityWithUniqueName getPersistBaseEntityWithUniqueName(BaseEntityWithUniqueName entity) {
		BaseEntityWithUniqueName result;

		Class<? extends BaseEntityWithUniqueName> entityClass = entity.getClass();
		String name = entity.getName();

		if (!this.singleEntityCache.containsKey(entityClass)) {
			this.singleEntityCache.put(entityClass, new HashMap<>());
		}

		if (!this.singleEntityCache.get(entityClass).containsKey(name)) {
			BaseEntityWithUniqueNameRepository repository = entityMapFactory.getRepository(entityClass);
			result = repository.findFirstByName(name);

			if (result != null) {
				this.singleEntityCache.get(entityClass).put(name, result);
			}
			else {
				repository.save(entity);
				result = entity;
			}
		}
		else {
			result = this.singleEntityCache.get(entityClass).get(name);
		}

		return result;
	}

	public Iterable getAll(Class<? extends BaseEntityWithUniqueName> entClass) {
		return this.setEntityCache.computeIfAbsent(
			entClass,
			x -> entityMapFactory.getRepository(entClass).findAll()
		);
	}
}