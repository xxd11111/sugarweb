package com.sugarcoat.protocol.auto;

import java.util.Collection;

/**
 * 字典对象扫描  覆盖（允许新增，允许更新，允许删除）
 *
 * @author xxd
 * @since 2023/8/25
 */
public class SimpleAutoRegistry<T> extends AbstractAutoRegistry<T> {

    private final RegistryHandler<T> registryHandler;

    protected SimpleAutoRegistry(Scanner<T> scanner, String deleteLevel, String updateLevel, RegistryHandler<T> registryHandler) {
        super(scanner);
        this.registryHandler = registryHandler;
    }

    @Override
    public void doSave(Collection<T> objects) {
        if ("all".equals(registryHandler.getDeleteStrategy())) {
            registryHandler.deleteAll();
        }
        for (T object : objects) {
            T exist = registryHandler.selectOne(object);
            if (exist == null) {
                registryHandler.insert(object);
            } else {
                if ("update".equals(registryHandler.getUpdateStrategy())) {
                    registryHandler.modify(exist, object);
                } else if ("override".equals(registryHandler.getUpdateStrategy())) {
                    registryHandler.override(exist, object);
                }
            }
        }
    }
}
