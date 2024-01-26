package com.sugarweb.framework.orm;

import org.springframework.data.querydsl.ListQuerydslPredicateExecutor;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 仓库
 *
 * @author xxd
 * @version 1.0
 */
@NoRepositoryBean
public interface BaseRepository<T> extends ListCrudRepository<T, String>, ListQuerydslPredicateExecutor<T> {

}
