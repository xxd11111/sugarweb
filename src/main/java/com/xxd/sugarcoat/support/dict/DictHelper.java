package com.xxd.sugarcoat.support.dict;

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

    private static DictCache dictCache;

    protected static void init(DictCache dictCache) {
        DictHelper.dictCache = dictCache;
    }

    public static void saveDict(Collection<Dict> dicts) {
        dictCache.saveDict(dicts);
    }

    public static void saveDict(Dict dict) {
        dictCache.saveDict(dict);
    }

    public static void removeDict(String type) {
        dictCache.removeDict(type);
    }

    public static void removeDict(String type, String code) {
        dictCache.removeDict(type, code);
    }

    public static String getDict(String type, String code) {
        return dictCache.getDict(type, code);
    }

    public static Map<String, String> getDict(String type) {
        return dictCache.getDict(type);
    }

    public static void reload(Collection<Dict> dicts) {
        dictCache.reload(dicts);
    }

    public static void clear() {
        dictCache.clear();
    }
}
