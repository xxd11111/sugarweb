package com.sugarcoat.api.common;

import java.io.Serializable;

/**
 * 标识
 *
 * @author xxd
 * @date 2023/6/28 22:48
 */
public interface Flag<T extends Serializable> {

    String getCode();

}
