package com.sugarcoat.support.dict.domain;

import com.sugarcoat.api.dict.Dictionary;
import com.sugarcoat.api.dict.DictionaryGroup;

import java.util.List;

/**
 * 扫描项目中字典数据
 *
 * @author xxd
 * @since 2023/8/26
 */
public interface DictionaryScanner {

    List<DictionaryGroup> scan();

}
