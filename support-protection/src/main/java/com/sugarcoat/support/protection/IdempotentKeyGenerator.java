package com.sugarcoat.support.protection;

import org.aspectj.lang.JoinPoint;

public interface IdempotentKeyGenerator {

    /**
     * 生成幂等key
     * @param path request path
     * @param args 入参
     * @return key
     */
    String generator(String path, Object[] args);

}
