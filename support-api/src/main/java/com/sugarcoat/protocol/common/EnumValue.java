package com.sugarcoat.protocol.common;

import java.io.Serializable;

/**
 * 标识
 *
 * @author xxd
 * @since 2023/6/28 22:48
 */
public interface EnumValue<T extends Serializable> {

    T getValue();

}
