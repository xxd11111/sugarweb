package com.sugarcoat.cache;

import org.redisson.api.NameMapper;

/**
 * redis前缀key处理
 */
public class RedisKeyPrefixMapper implements NameMapper {

	private final String prefix;

	public RedisKeyPrefixMapper(String prefix) {
		this.prefix = prefix == null || prefix.isEmpty() ? "" : prefix + ":";
	}

	@Override
	public String map(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("未指定key");
		}
		return prefix + name;
	}

	@Override
	public String unmap(String name) {
		if (name == null || name.isEmpty()) {
			return null;
		}
		if (prefix != null) {
			return name.substring(prefix.length());
		}
		return name;
	}

}
