package com.github.bogdanovmn.translator.web.orm.repository.common;


import com.github.bogdanovmn.translator.web.orm.entity.common.BaseEntityWithUniqueName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseEntityWithUniqueNameRepository<EntityT extends BaseEntityWithUniqueName>
	extends CrudRepository<EntityT, Integer>
{
	EntityT findFirstByName(String name);
}