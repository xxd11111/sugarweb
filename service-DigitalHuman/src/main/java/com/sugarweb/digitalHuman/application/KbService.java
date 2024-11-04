package com.sugarweb.digitalHuman.application;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.application.dto.KbDetailDto;
import com.sugarweb.digitalHuman.application.dto.KbPageQuery;
import com.sugarweb.digitalHuman.application.dto.KbSaveDto;
import com.sugarweb.digitalHuman.application.dto.KbUpdateDto;
import com.sugarweb.digitalHuman.domain.KbInfo;
import com.sugarweb.framework.orm.PageHelper;

import java.time.LocalDateTime;

/**
 * 知识库服务
 *
 * @author xxd
 * @version 1.0
 */
public class KbService {
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
        kbInfo.setEmbeddingModelId(saveDto.getEmbeddingModel());
        kbInfo.setStatus(saveDto.getStatus());
        kbInfo.setDescription(saveDto.getDescription());
        kbInfo.setCreateTime(LocalDateTime.now());
        kbInfo.setUpdateTime(LocalDateTime.now());
        Db.save(kbInfo);
        return buildKbDetailDto(kbInfo);
    }

    public KbDetailDto update(KbUpdateDto updateDto) {
        KbInfo kbInfo = Db.getById(updateDto.getKbId(), KbInfo.class);
        if (kbInfo != null) {
            kbInfo.setKbName(updateDto.getKbName());
            kbInfo.setEmbeddingModelId(updateDto.getEmbeddingModel());
            kbInfo.setStatus(updateDto.getStatus());
            kbInfo.setDescription(updateDto.getDescription());
            kbInfo.setUpdateTime(LocalDateTime.now());
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
        Db.removeById(kbId, KbInfo.class);
    }
}
