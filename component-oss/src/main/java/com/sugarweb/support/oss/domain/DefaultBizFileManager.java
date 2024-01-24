package com.sugarweb.support.oss.domain;

import com.google.common.base.Strings;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.sugarweb.support.oss.api.BizFileManager;
import com.sugarweb.support.oss.api.FileInfo;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 文件业务绑定客户端
 *
 * @author xxd
 * @version 1.0
 * @version 1.0
 */
@RequiredArgsConstructor
public class DefaultBizFileManager implements BizFileManager {

    private final BaseBizFileRepository bizFileRepository;

    private final BaseFileInfoRepository fileInfoRepository;

    @Override
    public void associateBizFile(String fileGroup, String bizId, Collection<String> fileIds) {
        List<SgcFileBizInfo> sgcBizFiles = new ArrayList<>();
        for (String fileId : fileIds) {
            SgcFileBizInfo sgcBizFile = new SgcFileBizInfo();
            sgcBizFile.setFileId(fileId);
            sgcBizFile.setBizId(bizId);
            sgcBizFile.setBizType(fileGroup);
            sgcBizFiles.add(sgcBizFile);
        }
        bizFileRepository.saveAll(sgcBizFiles);
    }

    @Override
    public void associateBizFile(String fileGroup, String bizId, String fileId) {
        SgcFileBizInfo sgcBizFile = new SgcFileBizInfo();
        sgcBizFile.setFileId(fileId);
        sgcBizFile.setBizId(bizId);
        sgcBizFile.setBizType(fileGroup);
        bizFileRepository.save(sgcBizFile);
    }

    @Override
    public void separateByBizId(String bizId, String fileGroup) {
        QSgcFileBizInfo sgcBizFile = QSgcFileBizInfo.sgcFileBizInfo;
        BooleanExpression expression = sgcBizFile.bizType.eq(fileGroup)
                .and(sgcBizFile.bizId.eq(bizId));
        Iterable<SgcFileBizInfo> sgcBizFiles = bizFileRepository.findAll(expression);
        bizFileRepository.deleteAll(sgcBizFiles);
    }

    @Override
    public void separateByBizId(String bizId) {
        QSgcFileBizInfo sgcBizFile = QSgcFileBizInfo.sgcFileBizInfo;
        BooleanExpression expression = sgcBizFile.bizId.eq(bizId);
        Iterable<SgcFileBizInfo> sgcBizFiles = bizFileRepository.findAll(expression);
        bizFileRepository.deleteAll(sgcBizFiles);
    }

    @Override
    public void separateByFileId(String fileId) {
        QSgcFileBizInfo sgcBizFile = QSgcFileBizInfo.sgcFileBizInfo;
        BooleanExpression expression = sgcBizFile.fileId.eq(fileId);
        Iterable<SgcFileBizInfo> sgcBizFiles = bizFileRepository.findAll(expression);
        bizFileRepository.deleteAll(sgcBizFiles);
    }

    @Override
    public void separateByFileId(Collection<String> fileIds) {
        QSgcFileBizInfo sgcBizFile = QSgcFileBizInfo.sgcFileBizInfo;
        BooleanExpression expression = sgcBizFile.fileId.in(fileIds);
        Iterable<SgcFileBizInfo> sgcBizFiles = bizFileRepository.findAll(expression);
        bizFileRepository.deleteAll(sgcBizFiles);
    }

    @Override
    public List<FileInfo> listFile(String bizId, String fileGroup) {
        QSgcFileBizInfo sgcBizFile = QSgcFileBizInfo.sgcFileBizInfo;
        BooleanExpression expression = sgcBizFile.bizType.eq(fileGroup)
                .and(sgcBizFile.bizId.eq(bizId));
        Iterable<SgcFileBizInfo> bizFileIterable = bizFileRepository.findAll(expression);
        List<String> fileIds = new ArrayList<>();
        for (SgcFileBizInfo bizFile : bizFileIterable) {
            fileIds.add(bizFile.getFileId());
        }
        Iterable<SgcFileInfo> fileInfoIterable = fileInfoRepository.findAllById(fileIds);
        List<FileInfo> fileInfos = new ArrayList<>();
        fileInfoIterable.forEach(fileInfos::add);
        return fileInfos;
    }

    @Override
    public List<FileInfo> listFile(String bizId) {
        QSgcFileBizInfo sgcBizFile = QSgcFileBizInfo.sgcFileBizInfo;
        BooleanExpression expression = sgcBizFile.bizId.eq(bizId);
        Iterable<SgcFileBizInfo> bizFileIterable = bizFileRepository.findAll(expression);
        List<String> fileIds = new ArrayList<>();
        for (SgcFileBizInfo bizFile : bizFileIterable) {
            fileIds.add(bizFile.getFileId());
        }
        Iterable<SgcFileInfo> fileInfoIterable = fileInfoRepository.findAllById(fileIds);
        List<FileInfo> fileInfos = new ArrayList<>();
        fileInfoIterable.forEach(fileInfos::add);
        return fileInfos;
    }

    @Override
    public Optional<FileInfo> getTopFile(String bizId, String fileGroup) {
        QSgcFileBizInfo sgcBizFile = QSgcFileBizInfo.sgcFileBizInfo;
        BooleanExpression expression = sgcBizFile.bizId.eq(bizId);
        Iterable<SgcFileBizInfo> bizFileIterable = bizFileRepository.findAll(expression);
        String fileId = null;
        for (SgcFileBizInfo bizFile : bizFileIterable) {
            fileId = bizFile.getFileId();
            break;
        }
        if (Strings.isNullOrEmpty(fileId)) {
            return Optional.empty();
        }
        return fileInfoRepository.findById(fileId).map(a -> a);
    }

    @Override
    public Optional<FileInfo> getFile(String fileId) {
		return fileInfoRepository.findById(fileId).map(a -> a);
	}

}
