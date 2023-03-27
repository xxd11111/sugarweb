package com.xxd.sugarcoat.support.orm.api;

import java.io.Serializable;

/**
 * @author xxd
 * @description TODO
 * @date 2023/3/27 20:42
 */
public interface BaseRepository<T> {
    boolean save(T DO);

    boolean update(T DO);

    boolean remove(Serializable id);

    T find(Serializable id);
}
