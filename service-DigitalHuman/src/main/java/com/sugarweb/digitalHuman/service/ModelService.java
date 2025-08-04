package com.sugarweb.digitalHuman.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.entity.Model;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.orm.PageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ModelService
 *
 * @author xxd
 * @version 1.0
 */
@Service
public class ModelService {

    public Model save(Model model) {
        Db.save(model);
        return model;
    }

    public Model update(Model model) {
        Db.updateById(model);
        return model;
    }

    public void remove(String modelId) {
        Db.removeById(modelId, Model.class);
    }

    public Model getById(String modelId) {
        return Db.getById(modelId, Model.class);
    }

    public List<Model> list() {
        return Db.lambdaQuery(Model.class).list();
    }

    public IPage<Model> page(PageQuery pageQuery) {
        return Db.lambdaQuery(Model.class).page(PageHelper.getPage(pageQuery));
    }


}
