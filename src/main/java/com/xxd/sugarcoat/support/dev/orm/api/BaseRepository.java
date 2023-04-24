package com.xxd.sugarcoat.support.dev.orm.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;


/**
 * @author xxd
 * @description TODO
 * @date 2023/3/27 20:42
 */
@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, String>, QuerydslPredicateExecutor<T> {



}
