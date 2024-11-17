package com.sugarweb.digitalHuman.application;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.application.dto.DatasetDetailDto;
import com.sugarweb.digitalHuman.application.dto.DatasetPageQuery;
import com.sugarweb.digitalHuman.application.dto.DatasetSaveDto;
import com.sugarweb.digitalHuman.application.dto.DatasetUpdateDto;
import com.sugarweb.digitalHuman.config.ApplicationProperties;
import com.sugarweb.digitalHuman.domain.Dataset;
import com.sugarweb.digitalHuman.domain.Model;
import com.sugarweb.digitalHuman.infra.MilvusEmbeddingStoreFactory;
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
public class DatasetService {

    @Resource
    private ApplicationProperties applicationProperties;
    @Autowired
    private ModelService modelService;

    public IPage<DatasetDetailDto> page(DatasetPageQuery query) {
        return Db.page(PageHelper.getPage(query), new LambdaQueryWrapper<>(Dataset.class)
                // .like(StrUtil.isNotEmpty(query.get()), Dataset::getDatasetName, query.getDatasetName())
        ).convert(this::buildKbDetailDto);
    }

    public DatasetDetailDto detail(String datasetId) {
        Dataset dataset = Db.getById(datasetId, Dataset.class);
        return buildKbDetailDto(dataset);
    }

    public DatasetDetailDto save(DatasetSaveDto saveDto) {
        Dataset dataset = new Dataset();
        // dataset.setDatasetName(saveDto.getDatasetName());
        dataset.setDescription(saveDto.getDescription());

        //绑定向量模型
        Model model = Db.getById(saveDto.getEmbeddingModelId(), Model.class);
        if (model == null) {
            throw new ValidateException("模型未配置");
        }
        dataset.setEmbeddingModelId(saveDto.getEmbeddingModelId());
        dataset.setEmbeddingModelName(model.getModelName());
        dataset.setDimension(model.getDimension());

        //绑定向量库
        bindVectorDatabase(dataset);

        Db.save(dataset);
        return buildKbDetailDto(dataset);
    }

    public DatasetDetailDto update(DatasetUpdateDto updateDto) {
        Dataset dataset = Db.getById(updateDto.getDatasetId(), Dataset.class);
        if (dataset != null) {
            dataset.setDatasetName(updateDto.getDatasetName());
            dataset.setStatus(updateDto.getStatus());
            dataset.setDescription(updateDto.getDescription());
            Db.updateById(dataset);
        }
        return buildKbDetailDto(dataset);
    }

    private DatasetDetailDto buildKbDetailDto(Dataset dataset) {
        if (dataset == null) {
            return null;
        }
        DatasetDetailDto datasetDetailDto = new DatasetDetailDto();
        datasetDetailDto.setDatasetId(dataset.getDatasetId());
        datasetDetailDto.setDatasetName(dataset.getDatasetName());
        datasetDetailDto.setCollectionName(dataset.getCollectionName());
        datasetDetailDto.setEmbeddingModel(dataset.getEmbeddingModelId());
        datasetDetailDto.setDimension(dataset.getDimension());
        datasetDetailDto.setStatus(dataset.getStatus());
        datasetDetailDto.setDescription(dataset.getDescription());
        return datasetDetailDto;
    }

    public void remove(String datasetId) {
        Dataset dataset = Db.getById(datasetId, Dataset.class);
        if (dataset == null) {
            throw new ValidateException("知识库不存在");
        }
        Db.removeById(datasetId, Dataset.class);
        MilvusEmbeddingStore milvusEmbeddingStore = MilvusEmbeddingStoreFactory.create(dataset.getCollectionName(), dataset.getDimension());
        milvusEmbeddingStore.dropCollection(dataset.getCollectionName());
    }

    private void bindVectorDatabase(Dataset dataset) {
        String collectionName = IdUtil.randomUUID();
        dataset.setCollectionName(collectionName);

        MilvusEmbeddingStoreFactory.create(dataset.getCollectionName(), dataset.getDimension());
    }

    public Dataset getById(String datasetId) {
        Dataset dataset = Db.getById(datasetId, Dataset.class);
        if (StrUtil.isNotEmpty(dataset.getEmbeddingModelId())){
            Model model = modelService.getOne(dataset.getEmbeddingModelId());
            dataset.setEmbeddingModel(model);
        }
        return dataset;
    }
}
