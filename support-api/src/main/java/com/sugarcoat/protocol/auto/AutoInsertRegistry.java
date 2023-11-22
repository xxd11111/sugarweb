package com.sugarcoat.protocol.auto;

import java.util.Collection;

/**
 * 字典对象注册策略，只新增
 *
 * @author xxd
 * @since 2023/8/25
 */
public class AutoInsertRegistry extends AbstractAutoRegistry {

    private final RegistryHandler registryHandler;

    protected AutoInsertRegistry(Scanner scanner, RegistryHandler registryHandler) {
        super(scanner);
        this.registryHandler = registryHandler;
    }

    @Override
    public void doSave(Collection<Object> objects) {
        for (Object object : objects) {
            Object exist = registryHandler.exist(object);
            if (exist == null) {
                registryHandler.insert(object);
            }
        }
    }

}
