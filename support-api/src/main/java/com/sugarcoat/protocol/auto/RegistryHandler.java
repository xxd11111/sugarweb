package com.sugarcoat.protocol.auto;

import java.util.Collection;

/**
 * TODO
 *
 * @author 许向东
 * @date 2023/11/22
 */
public interface RegistryHandler {

    void insert(Object o);

    void update(Object o);

    Object modify(Object db, Object scan);

    void delete(Collection<Object> objects);

    Object exist(Object o);


}
