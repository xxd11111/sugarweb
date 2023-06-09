package com.sugarcoat.orm;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;


/**
 * 仓库
 *
 * @author xxd
 * @date 2023/3/27 20:42
 */
@NoRepositoryBean
public interface SugarcoatRepository<T> extends CrudRepository<T, String>, QuerydslPredicateExecutor<T> {

}
