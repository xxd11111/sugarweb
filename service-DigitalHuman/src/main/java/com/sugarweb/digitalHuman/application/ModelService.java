package com.sugarweb.digitalHuman.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.domain.ModelInfo;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.orm.PageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
@Service
public class ModelService {

    public ModelInfo save(ModelInfo modelInfo) {
        Db.save(modelInfo);
        return modelInfo;
    }

    public ModelInfo update(ModelInfo modelInfo) {
        Db.updateById(modelInfo);
        return modelInfo;
    }

    public void remove(String modelId) {
        Db.removeById(modelId, ModelInfo.class);
    }

    public ModelInfo getOne(String modelId) {
        return Db.getById(modelId, ModelInfo.class);
    }

    public List<ModelInfo> list() {
        return Db.lambdaQuery(ModelInfo.class).list();
    }

    public IPage<ModelInfo> page(PageQuery pageQuery) {
        return Db.lambdaQuery(ModelInfo.class).page(PageHelper.getPage(pageQuery));
    }


}
