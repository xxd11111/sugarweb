package com.sugarcoat.support.protection;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DefaultKeyGenerator implements IdempotentKeyGenerator {

    private static final String JOIN_CHAR = ",";

    @Override
    public String generator(String path, Object[] args) {
        String join = StrUtil.join(JOIN_CHAR, args);
        return DigestUtil.md5Hex(path + JOIN_CHAR + join);
    }
}
