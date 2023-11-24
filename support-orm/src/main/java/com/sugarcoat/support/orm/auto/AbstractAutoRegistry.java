package com.sugarcoat.support.orm.auto;

import cn.hutool.core.collection.CollUtil;

import java.util.Collection;

/**
 * 自动注册
 *
 * @author xxd
 * @since 2023/8/25
 */
public abstract class AbstractAutoRegistry<T> implements Registry {

    private final Scanner<T> scanner;

    protected AbstractAutoRegistry(Scanner<T> scanner) {
        this.scanner = scanner;
    }

    @Override
    public void register() {
        Collection<T> scan = scanner.scan();
        if (CollUtil.isEmpty(scan)) {
            return;
        }
        doSave(scan);
    }

    public abstract void doSave(Collection<T> objects);

}
