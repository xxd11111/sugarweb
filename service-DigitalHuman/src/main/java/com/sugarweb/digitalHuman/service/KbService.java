package com.sugarweb.digitalHuman.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.service.dto.KbDetailDto;
import com.sugarweb.digitalHuman.service.dto.DatasetPageQuery;
import com.sugarweb.digitalHuman.service.dto.KbSaveDto;
import com.sugarweb.digitalHuman.service.dto.KbUpdateDto;
import com.sugarweb.digitalHuman.config.ApplicationProperties;
import com.sugarweb.digitalHuman.entity.Kb;
import com.sugarweb.digitalHuman.entity.Model;
import com.sugarweb.digitalHuman.component.MilvusEmbeddingStoreFactory;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.framework.orm.PageHelper;
import dev.langchain4j.store.embedding.milvus.MilvusEmbeddingStore;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 知识库服务
 *
 * @author xxd
 * @version 1.0
 */
@Service
public class KbService {

    @Resource
    private ApplicationProperties applicationProperties;
    @Autowired
    private ModelService modelService;

    public IPage<KbDetailDto> page(DatasetPageQuery query) {
        return Db.page(PageHelper.getPage(query), new LambdaQueryWrapper<>(Kb.class)
                // .like(StrUtil.isNotEmpty(query.get()), Dataset::getDatasetName, query.getDatasetName())
        ).convert(this::buildKbDetailDto);
    }

    public KbDetailDto detail(String datasetId) {
        Kb kb = Db.getById(datasetId, Kb.class);
        return buildKbDetailDto(kb);
    }

    public KbDetailDto save(KbSaveDto saveDto) {
        Kb kb = new Kb();
        // dataset.setDatasetName(saveDto.getDatasetName());
        kb.setDescription(saveDto.getDescription());

        //绑定向量模型
        Model model = Db.getById(saveDto.getEmbeddingModelId(), Model.class);
        if (model == null) {
            throw new ValidateException("模型未配置");
        }
        kb.setEmbeddingModelId(saveDto.getEmbeddingModelId());
        kb.setEmbeddingModelName(model.getModelName());
        kb.setDimension(model.getDimension());

        //绑定向量库
        bindVectorDatabase(kb);

        Db.save(kb);
        return buildKbDetailDto(kb);
    }

    public KbDetailDto update(KbUpdateDto updateDto) {
        Kb kb = Db.getById(updateDto.getDatasetId(), Kb.class);
        if (kb != null) {
            kb.setKbName(updateDto.getDatasetName());
            kb.setStatus(updateDto.getStatus());
            kb.setDescription(updateDto.getDescription());
            Db.updateById(kb);
        }
        return buildKbDetailDto(kb);
    }

    private KbDetailDto buildKbDetailDto(Kb kb) {
        if (kb == null) {
            return null;
        }
        KbDetailDto kbDetailDto = new KbDetailDto();
        kbDetailDto.setDatasetId(kb.getKbId());
        kbDetailDto.setDatasetName(kb.getKbName());
        kbDetailDto.setCollectionName(kb.getCollectionName());
        kbDetailDto.setEmbeddingModel(kb.getEmbeddingModelId());
        kbDetailDto.setDimension(kb.getDimension());
        kbDetailDto.setStatus(kb.getStatus());
        kbDetailDto.setDescription(kb.getDescription());
        return kbDetailDto;
    }

    public void remove(String datasetId) {
        Kb kb = Db.getById(datasetId, Kb.class);
        if (kb == null) {
            throw new ValidateException("知识库不存在");
        }
        Db.removeById(datasetId, Kb.class);
        MilvusEmbeddingStore milvusEmbeddingStore = MilvusEmbeddingStoreFactory.create(kb.getCollectionName(), kb.getDimension());
        milvusEmbeddingStore.dropCollection(kb.getCollectionName());
    }

    private void bindVectorDatabase(Kb kb) {
        String collectionName = IdUtil.randomUUID();
        kb.setCollectionName(collectionName);

        MilvusEmbeddingStoreFactory.create(kb.getCollectionName(), kb.getDimension());
    }

    public Kb getById(String datasetId) {
        Kb kb = Db.getById(datasetId, Kb.class);
        if (StrUtil.isNotEmpty(kb.getEmbeddingModelId())){
            Model model = modelService.getOne(kb.getEmbeddingModelId());
            kb.setEmbeddingModel(model);
        }
        return kb;
    }
}
