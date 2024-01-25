package com.sugarweb.framework.common;

import java.io.Serializable;

/**
 * 标识
 *
 * @author xxd
 * @version 1.0
 */
public interface EnumValue<T extends Serializable> {

    T getValue();

}
