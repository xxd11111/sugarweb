package com.sugarcoat.support.protection.idempotent;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.sugarcoat.protocol.protection.IdempotentKeyGenerator;
import org.springframework.stereotype.Component;

@Component
public class DefaultKeyGenerator implements IdempotentKeyGenerator {

    private static final String JOIN_CHAR = ",";

    @Override
    public String generator(String path, Object[] args) {
        String join = StrUtil.join(JOIN_CHAR, args);
        return DigestUtil.md5Hex(path + JOIN_CHAR + join);
    }
}
