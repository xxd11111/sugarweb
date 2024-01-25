package com.sugarweb.support.oss.application;

import com.google.common.base.Strings;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.sugarweb.support.oss.domain.*;
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
 */
@RequiredArgsConstructor
public class FileBizServiceImpl implements FileBizService {

    private final FileBizRepository fileBizRepository;

    private final FileInfoRepository fileInfoRepository;

    @Override
    public void associateBizFile(String fileGroup, String bizId, Collection<String> fileIds) {
        List<FileBizInfo> sgcBizFiles = new ArrayList<>();
        for (String fileId : fileIds) {
            FileBizInfo sgcBizFile = new FileBizInfo();
            sgcBizFile.setFileId(fileId);
            sgcBizFile.setBizId(bizId);
            sgcBizFile.setBizType(fileGroup);
            sgcBizFiles.add(sgcBizFile);
        }
        fileBizRepository.saveAll(sgcBizFiles);
    }

    @Override
    public void associateBizFile(String fileGroup, String bizId, String fileId) {
        FileBizInfo sgcBizFile = new FileBizInfo();
        sgcBizFile.setFileId(fileId);
        sgcBizFile.setBizId(bizId);
        sgcBizFile.setBizType(fileGroup);
        fileBizRepository.save(sgcBizFile);
    }

    @Override
    public void separateByBizId(String bizId, String fileGroup) {
        QFileBizInfo sgcBizFile = QFileBizInfo.fileBizInfo;
        BooleanExpression expression = sgcBizFile.bizType.eq(fileGroup)
                .and(sgcBizFile.bizId.eq(bizId));
        Iterable<FileBizInfo> sgcBizFiles = fileBizRepository.findAll(expression);
        fileBizRepository.deleteAll(sgcBizFiles);
    }

    @Override
    public void separateByBizId(String bizId) {
        QFileBizInfo sgcBizFile = QFileBizInfo.fileBizInfo;
        BooleanExpression expression = sgcBizFile.bizId.eq(bizId);
        Iterable<FileBizInfo> sgcBizFiles = fileBizRepository.findAll(expression);
        fileBizRepository.deleteAll(sgcBizFiles);
    }

    @Override
    public void separateByFileId(String fileId) {
        QFileBizInfo sgcBizFile = QFileBizInfo.fileBizInfo;
        BooleanExpression expression = sgcBizFile.fileId.eq(fileId);
        Iterable<FileBizInfo> sgcBizFiles = fileBizRepository.findAll(expression);
        fileBizRepository.deleteAll(sgcBizFiles);
    }

    @Override
    public void separateByFileId(Collection<String> fileIds) {
        QFileBizInfo sgcBizFile = QFileBizInfo.fileBizInfo;
        BooleanExpression expression = sgcBizFile.fileId.in(fileIds);
        Iterable<FileBizInfo> sgcBizFiles = fileBizRepository.findAll(expression);
        fileBizRepository.deleteAll(sgcBizFiles);
    }

    @Override
    public List<FileInfo> listFile(String bizId, String fileGroup) {
        QFileBizInfo sgcBizFile = QFileBizInfo.fileBizInfo;
        BooleanExpression expression = sgcBizFile.bizType.eq(fileGroup)
                .and(sgcBizFile.bizId.eq(bizId));
        Iterable<FileBizInfo> bizFileIterable = fileBizRepository.findAll(expression);
        List<String> fileIds = new ArrayList<>();
        for (FileBizInfo bizFile : bizFileIterable) {
            fileIds.add(bizFile.getFileId());
        }
        Iterable<FileInfo> fileInfoIterable = fileInfoRepository.findAllById(fileIds);
        List<FileInfo> files = new ArrayList<>();
        fileInfoIterable.forEach(files::add);
        return files;
    }

    @Override
    public List<FileInfo> listFile(String bizId) {
        QFileBizInfo sgcBizFile = QFileBizInfo.fileBizInfo;
        BooleanExpression expression = sgcBizFile.bizId.eq(bizId);
        Iterable<FileBizInfo> bizFileIterable = fileBizRepository.findAll(expression);
        List<String> fileIds = new ArrayList<>();
        for (FileBizInfo bizFile : bizFileIterable) {
            fileIds.add(bizFile.getFileId());
        }
        Iterable<FileInfo> fileInfoIterable = fileInfoRepository.findAllById(fileIds);
        List<FileInfo> files = new ArrayList<>();
        fileInfoIterable.forEach(files::add);
        return files;
    }

    @Override
    public Optional<FileInfo> getTopFile(String bizId, String fileGroup) {
        QFileBizInfo sgcBizFile = QFileBizInfo.fileBizInfo;
        BooleanExpression expression = sgcBizFile.bizId.eq(bizId);
        Iterable<FileBizInfo> bizFileIterable = fileBizRepository.findAll(expression);
        String fileId = null;
        for (FileBizInfo bizFile : bizFileIterable) {
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
