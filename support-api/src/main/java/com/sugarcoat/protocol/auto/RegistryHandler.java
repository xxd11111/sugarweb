package com.sugarcoat.protocol.auto;

import java.util.Collection;

/**
 * 注册处理
 *
 * @author 许向东
 * @date 2023/11/22
 */
public interface RegistryHandler {

    void insert(Object o);

    void override(Object db, Object scan);

    void modify(Object db, Object scan);

    void deleteByCondition(Collection<Object> objects);

    void deleteAll();

    Object selectOne(Object o);

}
