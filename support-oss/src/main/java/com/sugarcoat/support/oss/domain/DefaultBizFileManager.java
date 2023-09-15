package com.sugarcoat.support.oss.domain;

import cn.hutool.core.util.StrUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.sugarcoat.api.oss.BizFileManager;
import com.sugarcoat.api.oss.FileInfo;
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
 * @since 2023/5/31
 */
@RequiredArgsConstructor
public class DefaultBizFileManager implements BizFileManager {

    private final SgcBizFileRepository bizFileRepository;

    private final SgcFileInfoRepository fileInfoRepository;

    @Override
    public void associateBizFile(String fileGroup, String bizId, Collection<String> fileIds) {
        List<SgcBizFile> sgcBizFiles = new ArrayList<>();
        for (String fileId : fileIds) {
            SgcBizFile sgcBizFile = new SgcBizFile();
            sgcBizFile.setFileId(fileId);
            sgcBizFile.setBizId(bizId);
            sgcBizFile.setFileGroup(fileGroup);
            sgcBizFiles.add(sgcBizFile);
        }
        bizFileRepository.saveAll(sgcBizFiles);
    }

    @Override
    public void associateBizFile(String fileGroup, String bizId, String fileId) {
        SgcBizFile sgcBizFile = new SgcBizFile();
        sgcBizFile.setFileId(fileId);
        sgcBizFile.setBizId(bizId);
        sgcBizFile.setFileGroup(fileGroup);
        bizFileRepository.save(sgcBizFile);
    }

    @Override
    public void separateByBizId(String bizId, String fileGroup) {
        QSgcBizFile sgcBizFile = QSgcBizFile.sgcBizFile;
        BooleanExpression expression = sgcBizFile.fileGroup.eq(fileGroup)
                .and(sgcBizFile.bizId.eq(bizId));
        Iterable<SgcBizFile> sgcBizFiles = bizFileRepository.findAll(expression);
        bizFileRepository.deleteAll(sgcBizFiles);
    }

    @Override
    public void separateByBizId(String bizId) {
        QSgcBizFile sgcBizFile = QSgcBizFile.sgcBizFile;
        BooleanExpression expression = sgcBizFile.bizId.eq(bizId);
        Iterable<SgcBizFile> sgcBizFiles = bizFileRepository.findAll(expression);
        bizFileRepository.deleteAll(sgcBizFiles);
    }

    @Override
    public void separateByFileId(String fileId) {
        QSgcBizFile sgcBizFile = QSgcBizFile.sgcBizFile;
        BooleanExpression expression = sgcBizFile.fileId.eq(fileId);
        Iterable<SgcBizFile> sgcBizFiles = bizFileRepository.findAll(expression);
        bizFileRepository.deleteAll(sgcBizFiles);
    }

    @Override
    public void separateByFileId(Collection<String> fileIds) {
        QSgcBizFile sgcBizFile = QSgcBizFile.sgcBizFile;
        BooleanExpression expression = sgcBizFile.fileId.in(fileIds);
        Iterable<SgcBizFile> sgcBizFiles = bizFileRepository.findAll(expression);
        bizFileRepository.deleteAll(sgcBizFiles);
    }

    @Override
    public List<FileInfo> listFile(String bizId, String fileGroup) {
        QSgcBizFile sgcBizFile = QSgcBizFile.sgcBizFile;
        BooleanExpression expression = sgcBizFile.fileGroup.eq(fileGroup)
                .and(sgcBizFile.bizId.eq(bizId));
        Iterable<SgcBizFile> bizFileIterable = bizFileRepository.findAll(expression);
        List<String> fileIds = new ArrayList<>();
        for (SgcBizFile bizFile : bizFileIterable) {
            fileIds.add(bizFile.getFileId());
        }
        Iterable<SgcFileInfo> fileInfoIterable = fileInfoRepository.findAllById(fileIds);
        List<FileInfo> fileInfos = new ArrayList<>();
        fileInfoIterable.forEach(fileInfos::add);
        return fileInfos;
    }

    @Override
    public List<FileInfo> listFile(String bizId) {
        QSgcBizFile sgcBizFile = QSgcBizFile.sgcBizFile;
        BooleanExpression expression = sgcBizFile.bizId.eq(bizId);
        Iterable<SgcBizFile> bizFileIterable = bizFileRepository.findAll(expression);
        List<String> fileIds = new ArrayList<>();
        for (SgcBizFile bizFile : bizFileIterable) {
            fileIds.add(bizFile.getFileId());
        }
        Iterable<SgcFileInfo> fileInfoIterable = fileInfoRepository.findAllById(fileIds);
        List<FileInfo> fileInfos = new ArrayList<>();
        fileInfoIterable.forEach(fileInfos::add);
        return fileInfos;
    }

    @Override
    public Optional<FileInfo> getTopFile(String bizId, String fileGroup) {
        QSgcBizFile sgcBizFile = QSgcBizFile.sgcBizFile;
        BooleanExpression expression = sgcBizFile.bizId.eq(bizId);
        Iterable<SgcBizFile> bizFileIterable = bizFileRepository.findAll(expression);
        String fileId = null;
        for (SgcBizFile bizFile : bizFileIterable) {
            fileId = bizFile.getFileId();
            break;
        }
        if (StrUtil.isEmpty(fileId)) {
            return Optional.empty();
        }
        return fileInfoRepository.findById(fileId).map(a -> a);
    }

    @Override
    public Optional<FileInfo> getFile(String fileId) {
		return fileInfoRepository.findById(fileId).map(a -> a);
	}

}
