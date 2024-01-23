package com.xxd.orm;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 仓库
 *
 * @author xxd
 * @version 1.0
 */
@NoRepositoryBean
public interface SgcRepository<T> extends CrudRepository<T, String>, QuerydslPredicateExecutor<T> {

}
