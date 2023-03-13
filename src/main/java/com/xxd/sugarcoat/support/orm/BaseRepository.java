package com.xxd.sugarcoat.support.orm;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Collection;

/**
 * @author xxd
 * @description 基础持久化仓库
 * @date 2022-12-16
 */
public interface BaseRepository<T> extends BaseMapper<T> {

    default int insert(Collection<T> entities){
        int count = 0;
        for (T entity : entities) {
            count += insert(entity);
        }
        return count;
    }
}
