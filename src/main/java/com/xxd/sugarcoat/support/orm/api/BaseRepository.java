package com.xxd.sugarcoat.support.orm.api;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;


/**
 * @author xxd
 * @description TODO
 * @date 2023/3/27 20:42
 */
@NoRepositoryBean
public interface BaseRepository<T> extends CrudRepository<T, String>, QuerydslPredicateExecutor<T> {

}