package com.xxd.sugarcoat.support.cache;

import cn.hutool.core.util.StrUtil;
import org.redisson.api.NameMapper;

/**
 * redis前缀key处理
 *
 */
public class RedisKeyPrefixMapper implements NameMapper {

    private final String prefix;

    public RedisKeyPrefixMapper(String prefix) {
        this.prefix = StrUtil.isBlank(prefix) ? "" : prefix + ":";
    }

    @Override
    public String map(String name) {
        if (StrUtil.isBlank(name)) {
            return null;
        }
        if (StrUtil.isNotBlank(prefix) && !name.startsWith(prefix)) {
            return prefix + name;
        }
        return name;
    }

    @Override
    public String unmap(String name) {
        if (StrUtil.isBlank(name)) {
            return null;
        }
        if (StrUtil.isNotBlank(prefix) && name.startsWith(prefix)) {
            return name.substring(prefix.length());
        }
        return name;
    }

}
