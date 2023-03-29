package com.xxd.sugarcoat.support.dev.dict;

import com.xxd.sugarcoat.support.dev.dict.api.DictService;

import java.util.Map;

/**
 * @author xxd
 * @description 字典工具类
 * @date 2022-11-18
 */
public class DictHelper {
    private DictHelper() {
    }

    protected static DictService dictService;

    public static String getDict(String type, String code) {
        return dictService.get(type, code);
    }

    public static Map<String, String> getDict(String type) {
        return dictService.get(type);
    }

}
