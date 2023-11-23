package com.sugarcoat.protocol.auto;

/**
 * 注册处理
 *
 * @author 许向东
 * @date 2023/11/22
 */
public interface RegistryHandler<T> {

    void insert(T o);

    void override(T db, T scan);

    void modify(T db, T scan);

    void deleteAll();

    T selectOne(T o);

    String getUpdateStrategy();

    String getDeleteStrategy();

}
