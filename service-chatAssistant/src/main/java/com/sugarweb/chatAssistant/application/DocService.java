package com.sugarweb.chatAssistant.application;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.chatAssistant.application.dto.DocDetailDto;
import com.sugarweb.chatAssistant.application.dto.DocPageQuery;
import com.sugarweb.chatAssistant.application.dto.DocSaveDto;
import com.sugarweb.chatAssistant.application.dto.DocUpdateDto;
import com.sugarweb.chatAssistant.domain.DocInfo;
import com.sugarweb.framework.orm.PageHelper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DocService
 *
 * @author xxd
 * @version 1.0
 */
public class DocService {
    public IPage<DocDetailDto> page(DocPageQuery query) {
        return Db.page(PageHelper.getPage(query), new LambdaQueryWrapper<>(DocInfo.class)
                .eq(DocInfo::getDocName, query.getDocName())
        ).convert(this::buildDocDetailDto);
    }

    public DocDetailDto detail(String docId) {
        DocInfo docInfo = Db.getById(docId, DocInfo.class);
        return buildDocDetailDto(docInfo);
    }

    public DocDetailDto save(DocSaveDto saveDto) {
        DocInfo docInfo = new DocInfo();
        docInfo.setKbId(saveDto.getKbId());
        docInfo.setDocName(saveDto.getDocName());
        docInfo.setSourceType(saveDto.getSourceType());
        docInfo.setDocStatus(saveDto.getDocStatus());
        docInfo.setParseStatus("1");
        docInfo.setCreateTime(LocalDateTime.now());
        docInfo.setUpdateTime(LocalDateTime.now());
        return buildDocDetailDto(docInfo);
    }

    public DocDetailDto update(DocUpdateDto updateDto) {
        DocInfo docInfo = Db.getById(updateDto.getDocId(), DocInfo.class);
        if (docInfo == null) {
            return null;
        }
        docInfo.setDocName(updateDto.getDocName());
        docInfo.setUpdateTime(LocalDateTime.now());
        Db.updateById(docInfo);
        return buildDocDetailDto(docInfo);
    }

    private DocDetailDto buildDocDetailDto(DocInfo docInfo) {
        if (docInfo == null) {
            return null;
        }
        DocDetailDto docDetailDto = new DocDetailDto();
        BeanUtil.copyProperties(docInfo, docDetailDto);
        return docDetailDto;
    }

    public void remove(String docId) {
        Db.removeById(docId, DocInfo.class);
    }

    public void parseStart(List<String> docIds) {

    }

    public void parseStop(List<String> docIds) {

    }

}
