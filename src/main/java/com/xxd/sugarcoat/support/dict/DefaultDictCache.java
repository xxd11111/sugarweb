package com.xxd.sugarcoat.support.dict;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xxd
 * @description 默认本地字典缓存
 * @date 2022-11-18
 */
@Component
public class DefaultDictCache implements DictCache {

    private final Map<String, ConcurrentHashMap<String, String>> dictMap = new ConcurrentHashMap<>();

    /**
     * 初始化锁
     */
    private static final Object INIT_LOCK = new Object();

    @Override
    public void saveDict(Dict dict) {
        if (StrUtil.isAllNotEmpty(dict.getType(), dict.getName())) {
            ConcurrentHashMap<String, String> codeMap = dictMap.get(dict.getType());
            //防止值丢失
            if (null == codeMap) {
                synchronized (INIT_LOCK) {
                    codeMap = dictMap.get(dict.getType());
                    if (null == codeMap) {
                        codeMap = new ConcurrentHashMap<>();
                        codeMap.put(dict.getCode(), dict.getName());
                        dictMap.put(dict.getType(), codeMap);
                    } else {
                        codeMap.put(dict.getCode(), dict.getName());
                    }
                }
            } else {
                codeMap.put(dict.getCode(), dict.getName());
            }
        }
    }

    @Override
    public void removeDict(String type) {
        dictMap.remove(type);
    }

    @Override
    public void removeDict(String type, String code) {
        if (StrUtil.isAllNotEmpty(type, code)) {
            ConcurrentHashMap<String, String> codeMap = dictMap.get(type);
            codeMap.remove(code);
        }
    }

    @Override
    public String getDict(String type, String code) {
        ConcurrentHashMap<String, String> codeMap = dictMap.get(type);
        return null == codeMap ? null : codeMap.get(code);
    }

    @Override
    public Map<String, String> getDict(String type) {
        Map<String, String> copyMap = new HashMap<>();
        BeanUtil.copyProperties(dictMap.get(type), copyMap);
        return copyMap;
    }

    @Override
    public void clear() {
        dictMap.clear();
    }

}
