package com.sugarcoat.protocol.auto;

import java.util.Collection;

/**
 * 字典对象扫描  覆盖（允许新增，允许更新，允许删除）
 *
 * @author xxd
 * @since 2023/8/25
 */
public class SimpleAutoRegistry extends AbstractAutoRegistry {

    private final String deleteLevel;

    private final String updateLevel;

    private final RegistryHandler registryHandler;

    protected SimpleAutoRegistry(Scanner scanner, String deleteLevel, String updateLevel, RegistryHandler registryHandler) {
        super(scanner);
        this.deleteLevel = deleteLevel;
        this.updateLevel = updateLevel;
        this.registryHandler = registryHandler;
    }

    @Override
    public void doSave(Collection<Object> objects) {
        if ("all".equals(deleteLevel)) {
            registryHandler.deleteAll();
        } else if ("condition".equals(deleteLevel)) {
            registryHandler.deleteByCondition(objects);
        }
        for (Object object : objects) {
            Object exist = registryHandler.selectOne(object);
            if (exist == null) {
                registryHandler.insert(object);
            } else {
                if ("update".equals(updateLevel)) {
                    registryHandler.modify(exist, object);
                } else if ("override".equals(updateLevel)) {
                    registryHandler.override(exist, object);
                }
            }
        }
    }
}
