package com.sugarcoat.protocol.auto;

import java.util.Collection;

/**
 * 字典对象扫描  覆盖（允许新增，允许更新，允许删除）
 *
 * @author xxd
 * @since 2023/8/25
 */
public class AutoOverrideRegistry extends AbstractAutoRegistry {

    private final RegistryHandler registryHandler;

    protected AutoOverrideRegistry(Scanner scanner, RegistryHandler registryHandler) {
        super(scanner);
        this.registryHandler = registryHandler;
    }


    @Override
    public void doSave(Collection<Object> objects) {
        registryHandler.delete(objects);
        for (Object object : objects) {
            Object exist = registryHandler.exist(object);
            if (exist == null) {
                registryHandler.insert(object);
            } else {
                Object modify = registryHandler.modify(exist, object);
                registryHandler.update(modify);
            }
        }
    }
}
