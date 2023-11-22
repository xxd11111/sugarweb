package com.sugarcoat.protocol.auto;

import java.util.Collection;

/**
 * 字典对象扫描,合并（允许新增，允许更新）
 *
 * @author xxd
 * @since 2023/8/25
 */
public class AutoMergeRegistry extends AbstractAutoRegistry {
    private final RegistryHandler registryHandler;

    protected AutoMergeRegistry(Scanner scanner, RegistryHandler registryHandler) {
        super(scanner);
        this.registryHandler = registryHandler;
    }

    @Override
    public void doSave(Collection<Object> objects) {
        for (Object object : objects) {
            Object exist = registryHandler.exist(object);
            if (exist == null) {
                registryHandler.insert(object);
            }else {
                Object modify = registryHandler.modify(exist, object);
                registryHandler.update(modify);
            }
        }
    }

}
