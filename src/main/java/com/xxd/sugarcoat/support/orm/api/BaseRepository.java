package com.xxd.sugarcoat.support.orm.api;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;


/**
 * @author xxd
 * @description TODO
 * @date 2023/3/27 20:42
 */
public interface BaseRepository<T> extends CrudRepository<T, Long> {

}
