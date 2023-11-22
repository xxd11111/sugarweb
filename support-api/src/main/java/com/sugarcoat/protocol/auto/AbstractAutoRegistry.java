package com.sugarcoat.protocol.auto;

import cn.hutool.core.collection.CollUtil;

import java.util.Collection;

/**
 * 字典对象注册策略，只新增
 *
 * @author xxd
 * @since 2023/8/25
 */
public abstract class AbstractAutoRegistry implements Registry {

    private final Scanner scanner;

    protected AbstractAutoRegistry(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void register() {
        Collection<Object> scan = scanner.scan();
        if (CollUtil.isEmpty(scan)) {
            return;
        }
        doSave(scan);
    }

    public abstract void doSave(Collection<Object> objects);

}
