package com.sugarcoat.protocol.auto;


import com.google.common.collect.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.Collection;

/**
 * ScanRegisterHandler
 *
 * @author 许向东
 * @date 2023/12/5
 */
public abstract class AbstractAutoRegistry<T> implements Registry, Scanner<T>, ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        register();
    }

    @Override
    public void register() {
        Collection<T> scans = this.scan();
        if (Iterables.isEmpty(scans)) {
            return;
        }
        this.deleteByCondition(scans);
        for (T object : scans) {
            T exist = this.selectOne(object);
            if (exist == null) {
                this.insert(object);
            } else {
                this.merge(exist, object);
            }
        }
    }

    protected abstract void insert(T o);

    protected abstract void merge(T db, T scan);

    protected abstract void deleteByCondition(Collection<T> collection);

    protected abstract T selectOne(T o);

}
