package com.xxd.sugarcoat.support.dict;

import com.xxd.sugarcoat.support.dict.api.BaseDict;
import com.xxd.sugarcoat.support.dict.api.DictService;

import java.util.Collection;
import java.util.Map;

/**
 * @author xxd
 * @description 字典工具类
 * @date 2022-11-18
 */
public class DictHelper {
    private DictHelper() {
    }

    private static DictService dictService;

    protected static void init(DictService dictService) {
        DictHelper.dictService = dictService;
    }

    public static void saveDict(Collection<BaseDict> baseDicts) {
        dictService.saveDict(baseDicts);
    }

    public static void saveDict(BaseDict baseDict) {
        dictService.saveDict(baseDict);
    }

    public static void removeDict(String type) {
        dictService.removeDict(type);
    }

    public static void removeDict(String type, String code) {
        dictService.removeDict(type, code);
    }

    public static String getDict(String type, String code) {
        return dictService.getDict(type, code);
    }

    public static Map<String, String> getDict(String type) {
        return dictService.getDict(type);
    }

    public static void reload(Collection<BaseDict> baseDicts) {
        dictService.reload(baseDicts);
    }

    public static void clear() {
        dictService.clear();
    }
}
