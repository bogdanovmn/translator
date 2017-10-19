package com.github.bogdanovmn.translator.web.orm;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseEntityWithUniqueNameRepository<EntityT extends BaseEntityWithUniqueName>
	extends CrudRepository<EntityT, Integer>
{
	EntityT findFirstByName(String name);
}