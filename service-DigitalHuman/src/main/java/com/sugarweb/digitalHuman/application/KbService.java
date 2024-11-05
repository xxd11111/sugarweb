package com.sugarweb.digitalHuman.application;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.application.dto.KbDetailDto;
import com.sugarweb.digitalHuman.application.dto.KbPageQuery;
import com.sugarweb.digitalHuman.application.dto.KbSaveDto;
import com.sugarweb.digitalHuman.application.dto.KbUpdateDto;
import com.sugarweb.digitalHuman.config.ChatAssistantProperties;
import com.sugarweb.digitalHuman.domain.KbInfo;
import com.sugarweb.digitalHuman.domain.ModelInfo;
import com.sugarweb.digitalHuman.infra.MilvusEmbeddingStoreFactory;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.framework.orm.PageHelper;
import dev.langchain4j.store.embedding.milvus.MilvusEmbeddingStore;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 知识库服务
 *
 * @author xxd
 * @version 1.0
 */
@Service
public class KbService {

    @Resource
    private ChatAssistantProperties.MilvusVectorStoreProperties vectorStoreProperties;
    @Resource
    private MilvusEmbeddingStore embeddingStore;

    public IPage<KbDetailDto> page(KbPageQuery query) {
        return Db.page(PageHelper.getPage(query), new LambdaQueryWrapper<>(KbInfo.class)
                .like(StrUtil.isNotEmpty(query.getKbName()), KbInfo::getKbName, query.getKbName())
        ).convert(this::buildKbDetailDto);
    }

    public KbDetailDto detail(String kbId) {
        KbInfo kbInfo = Db.getById(kbId, KbInfo.class);
        return buildKbDetailDto(kbInfo);
    }

    public KbDetailDto save(KbSaveDto saveDto) {
        KbInfo kbInfo = new KbInfo();
        kbInfo.setKbName(saveDto.getKbName());
        kbInfo.setStatus(saveDto.getStatus());
        kbInfo.setDescription(saveDto.getDescription());
        kbInfo.setCreateTime(LocalDateTime.now());
        kbInfo.setUpdateTime(LocalDateTime.now());

        //绑定向量模型
        ModelInfo modelInfo = Db.getById(saveDto.getEmbeddingModelId(), ModelInfo.class);
        if (modelInfo == null) {
            throw new ValidateException("模型未配置");
        }
        kbInfo.setEmbeddingModelId(saveDto.getEmbeddingModelId());
        kbInfo.setEmbeddingModelName(modelInfo.getModelName());
        kbInfo.setDimension(modelInfo.getDimension());

        //绑定向量库
        bindVectorDatabase(kbInfo);

        Db.save(kbInfo);
        return buildKbDetailDto(kbInfo);
    }

    public KbDetailDto update(KbUpdateDto updateDto) {
        KbInfo kbInfo = Db.getById(updateDto.getKbId(), KbInfo.class);
        if (kbInfo != null) {
            kbInfo.setKbName(updateDto.getKbName());
            kbInfo.setStatus(updateDto.getStatus());
            kbInfo.setDescription(updateDto.getDescription());
            kbInfo.setUpdateTime(LocalDateTime.now());

            ModelInfo modelInfo = Db.getById(updateDto.getEmbeddingModelId(), ModelInfo.class);
            if (modelInfo == null) {
                throw new ValidateException("模型未配置");
            }
            kbInfo.setEmbeddingModelId(updateDto.getEmbeddingModelId());
            kbInfo.setEmbeddingModelName(modelInfo.getModelName());
            kbInfo.setDimension(modelInfo.getDimension());
            Db.updateById(kbInfo);
        }
        return buildKbDetailDto(kbInfo);
    }

    private KbDetailDto buildKbDetailDto(KbInfo kbInfo) {
        if (kbInfo == null) {
            return null;
        }
        KbDetailDto kbDetailDto = new KbDetailDto();
        kbDetailDto.setKbId(kbInfo.getKbId());
        kbDetailDto.setKbName(kbInfo.getKbName());
        kbDetailDto.setCollectionName(kbInfo.getCollectionName());
        kbDetailDto.setEmbeddingModel(kbInfo.getEmbeddingModelId());
        kbDetailDto.setDimension(kbInfo.getDimension());
        kbDetailDto.setStatus(kbInfo.getStatus());
        kbDetailDto.setDescription(kbInfo.getDescription());
        kbDetailDto.setCreateTime(kbInfo.getCreateTime());
        kbDetailDto.setUpdateTime(kbInfo.getUpdateTime());
        return kbDetailDto;
    }

    public void remove(String kbId) {
        KbInfo kbInfo = Db.getById(kbId, KbInfo.class);
        if (kbInfo == null) {
            throw new ValidateException("知识库不存在");
        }
        embeddingStore.dropCollection(kbInfo.getCollectionName());
        Db.removeById(kbId, KbInfo.class);
    }

    private void bindVectorDatabase(KbInfo kbInfo) {
        String collectionName = IdUtil.randomUUID();
        kbInfo.setCollectionName(collectionName);

        MilvusEmbeddingStoreFactory.create(kbInfo.getCollectionName(), kbInfo.getDimension());
    }

}
