package com.sugarcoat.support.dict;

import com.sugarcoat.protocol.dictionary.DictionaryCode;
import com.sugarcoat.protocol.dictionary.DictionaryItem;
import com.sugarcoat.protocol.dictionary.InnerDictionary;
import com.sugarcoat.support.orm.BooleanEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * TODO
 *
 * @author 许向东
 * @date 2023/12/7
 */
@RequiredArgsConstructor
@Getter
@InnerDictionary
public enum DemoEnum {

    @DictionaryItem("e_aaa")
    AAA("1"),
    @DictionaryItem("e_bbb")
    BBB("2"),
    @DictionaryItem("e_ccc")
    CCC("3");

    private final static BooleanEnum BOOLEAN_ENUM = BooleanEnum.FALSE;
    @DictionaryCode
    private final String code;

}
