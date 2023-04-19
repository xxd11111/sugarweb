package com.xxd.sugarcoat.support.dev.dict;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.xxd.sugarcoat.support.dev.dict.api.DictDTO;
import com.xxd.sugarcoat.support.dev.dict.api.DictItem;
import com.xxd.sugarcoat.support.dev.dict.api.DictService;
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
public class DefaultDictServiceImpl implements DictService {

    private final Map<String, ConcurrentHashMap<String, String>> dictMap = new ConcurrentHashMap<>();

    /**
     * 初始化锁
     */
    private static final Object INIT_LOCK = new Object();
    @Override
    public void save(DictDTO dictDTO) {
        if (StrUtil.isAllNotEmpty(dictDTO.getGroupCode(), dictDTO.getName())) {
            ConcurrentHashMap<String, String> codeMap = dictMap.get(dictDTO.getGroupCode());
            //防止值丢失
            if (null == codeMap) {
                synchronized (INIT_LOCK) {
                    codeMap = dictMap.get(dictDTO.getGroupCode());
                    if (null == codeMap) {
                        codeMap = new ConcurrentHashMap<>();
                        codeMap.put(dictDTO.getCode(), dictDTO.getName());
                        dictMap.put(dictDTO.getGroupCode(), codeMap);
                    } else {
                        codeMap.put(dictDTO.getCode(), dictDTO.getName());
                    }
                }
            } else {
                codeMap.put(dictDTO.getCode(), dictDTO.getName());
            }
        }
    }

    @Override
    public void remove(String type) {
        dictMap.remove(type);
    }

    @Override
    public void remove(String type, String code) {
        if (StrUtil.isAllNotEmpty(type, code)) {
            ConcurrentHashMap<String, String> codeMap = dictMap.get(type);
            codeMap.remove(code);
        }
    }

    @Override
    public String get(String type, String code) {
        ConcurrentHashMap<String, String> codeMap = dictMap.get(type);
        return null == codeMap ? null : codeMap.get(code);
    }

    @Override
    public Map<String, String> get(String type) {
        Map<String, String> copyMap = new HashMap<>();
        BeanUtil.copyProperties(dictMap.get(type), copyMap);
        return copyMap;
    }

    @Override
    public void clear() {
        dictMap.clear();
    }

}
