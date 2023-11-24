package com.sugarcoat.support.dict.auto;

import com.sugarcoat.support.orm.auto.RegistryHandler;
import com.sugarcoat.protocol.dictionary.DictionaryManager;
import com.sugarcoat.support.dict.domain.SugarcoatDictionary;
import com.sugarcoat.support.dict.domain.SugarcoatDictionaryManager;

/**
 * TODO
 *
 * @author 许向东
 * @date 2023/11/23
 */
public class DictionaryRegistryHandler implements RegistryHandler<SugarcoatDictionary> {

    private final String deleteLevel;

    private final String updateLevel;

    private final DictionaryManager<SugarcoatDictionary> dictionaryManager;

    public DictionaryRegistryHandler(String deleteLevel, String updateLevel, SugarcoatDictionaryManager dictionaryManager) {
        this.deleteLevel = deleteLevel;
        this.updateLevel = updateLevel;
        this.dictionaryManager = dictionaryManager;
    }

    @Override
    public void insert(SugarcoatDictionary o) {
        dictionaryManager.put(o);
    }

    @Override
    public void override(SugarcoatDictionary db, SugarcoatDictionary scan) {
        scan.setId(db.getId());
        dictionaryManager.put(scan);
    }

    @Override
    public void modify(SugarcoatDictionary db, SugarcoatDictionary scan) {
        scan.setId(db.getId());
        dictionaryManager.put(scan);
    }

    @Override
    public void deleteAll() {
        dictionaryManager.removeAll();
    }

    @Override
    public SugarcoatDictionary selectOne(SugarcoatDictionary o) {
        return dictionaryManager.get(o.getGroup(), o.getCode()).orElse(null);
    }

    @Override
    public String getUpdateStrategy() {
        return updateLevel;
    }

    @Override
    public String getDeleteStrategy() {
        return deleteLevel;
    }
}
