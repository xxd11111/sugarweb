package com.sugarcoat.support.dict.domain;

import com.sugarcoat.support.orm.SgcRepository;
import org.springframework.stereotype.Repository;

/**
 * 字典仓库
 *
 * @author xxd
 * @version 1.0
 * @since 2023/4/25
 */
@Repository
public interface SgcDictionaryRepository extends SgcRepository<SugarcoatDictionary> {

}
