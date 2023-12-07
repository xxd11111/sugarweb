package com.sugarcoat.support.dict;

import com.sugarcoat.protocol.dictionary.DictionaryItem;
import com.sugarcoat.protocol.dictionary.InnerDictionary;

/**
 * TODO
 *
 * @author 许向东
 * @date 2023/12/7
 */
@InnerDictionary
public class DemoClass {

    @DictionaryItem("xx")
    public static final String AAA = "1";

    @DictionaryItem("yy")
    public static final String BBB = "2";

    @DictionaryItem("zz")
    public static final String CCC = "3";

}
