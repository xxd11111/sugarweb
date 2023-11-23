package com.sugarcoat.support.dict.auto;

import com.sugarcoat.protocol.auto.AbstractAutoRegistry;
import com.sugarcoat.protocol.auto.Scanner;

import java.util.Collection;

/**
 * TODO
 *
 * @author xxd
 * @since 2023/11/23 22:40
 */
public class DictionaryAutoRegistry<T> extends AbstractAutoRegistry<T> {
    public DictionaryAutoRegistry(Scanner<T> scanner) {
        super(scanner);
    }

    @Override
    public void doSave(Collection<T> objects) {

    }
}
