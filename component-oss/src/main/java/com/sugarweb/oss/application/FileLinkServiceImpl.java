package com.sugarweb.oss.application;

import com.google.common.base.Strings;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.sugarweb.oss.domain.*;
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
public class FileLinkServiceImpl implements FileLinkService {

    private final FileLinkInfoRepository fileLinkInfoRepository;

    private final FileInfoRepository fileInfoRepository;

    @Override
    public void linkFiles(String bizId, String fileGroup, Collection<String> fileIds) {
        List<FileLinkInfo> sgcBizFiles = new ArrayList<>();
        for (String fileId : fileIds) {
            FileLinkInfo sgcBizFile = new FileLinkInfo();
            sgcBizFile.setFileId(fileId);
            sgcBizFile.setBizId(bizId);
            sgcBizFile.setFileGroup(fileGroup);
            sgcBizFiles.add(sgcBizFile);
        }
        fileLinkInfoRepository.saveAll(sgcBizFiles);
    }

    @Override
    public void linkFile(String bizId, String fileGroup, String fileId) {
        FileLinkInfo sgcBizFile = new FileLinkInfo();
        sgcBizFile.setFileId(fileId);
        sgcBizFile.setBizId(bizId);
        sgcBizFile.setFileGroup(fileGroup);
        fileLinkInfoRepository.save(sgcBizFile);
    }

    @Override
    public void breakFiles(String bizId, String fileGroup) {
        QFileLinkInfo sgcBizFile = QFileLinkInfo.fileBizInfo;
        BooleanExpression expression = sgcBizFile.fileGroup.eq(fileGroup)
                .and(sgcBizFile.bizId.eq(bizId));
        Iterable<FileLinkInfo> sgcBizFiles = fileLinkInfoRepository.findAll(expression);
        fileLinkInfoRepository.deleteAll(sgcBizFiles);
    }

    @Override
    public void breakFiles(String bizId) {
        QFileLinkInfo sgcBizFile = QFileLinkInfo.fileBizInfo;
        BooleanExpression expression = sgcBizFile.bizId.eq(bizId);
        Iterable<FileLinkInfo> sgcBizFiles = fileLinkInfoRepository.findAll(expression);
        fileLinkInfoRepository.deleteAll(sgcBizFiles);
    }

    @Override
    public void breakByFileId(String fileId) {
        QFileLinkInfo sgcBizFile = QFileLinkInfo.fileBizInfo;
        BooleanExpression expression = sgcBizFile.fileId.eq(fileId);
        Iterable<FileLinkInfo> sgcBizFiles = fileLinkInfoRepository.findAll(expression);
        fileLinkInfoRepository.deleteAll(sgcBizFiles);
    }

    @Override
    public void breakByFileIds(Collection<String> fileIds) {
        QFileLinkInfo sgcBizFile = QFileLinkInfo.fileBizInfo;
        BooleanExpression expression = sgcBizFile.fileId.in(fileIds);
        Iterable<FileLinkInfo> sgcBizFiles = fileLinkInfoRepository.findAll(expression);
        fileLinkInfoRepository.deleteAll(sgcBizFiles);
    }

    @Override
    public List<FileInfo> findAll(String bizId, String fileGroup) {
        QFileLinkInfo sgcBizFile = QFileLinkInfo.fileBizInfo;
        BooleanExpression expression = sgcBizFile.fileGroup.eq(fileGroup)
                .and(sgcBizFile.bizId.eq(bizId));
        Iterable<FileLinkInfo> bizFileIterable = fileLinkInfoRepository.findAll(expression);
        List<String> fileIds = new ArrayList<>();
        for (FileLinkInfo bizFile : bizFileIterable) {
            fileIds.add(bizFile.getFileId());
        }
        Iterable<FileInfo> fileInfoIterable = fileInfoRepository.findAllById(fileIds);
        List<FileInfo> files = new ArrayList<>();
        fileInfoIterable.forEach(files::add);
        return files;
    }

    @Override
    public List<FileInfo> findAll(String bizId) {
        QFileLinkInfo sgcBizFile = QFileLinkInfo.fileBizInfo;
        BooleanExpression expression = sgcBizFile.bizId.eq(bizId);
        Iterable<FileLinkInfo> bizFileIterable = fileLinkInfoRepository.findAll(expression);
        List<String> fileIds = new ArrayList<>();
        for (FileLinkInfo bizFile : bizFileIterable) {
            fileIds.add(bizFile.getFileId());
        }
        Iterable<FileInfo> fileInfoIterable = fileInfoRepository.findAllById(fileIds);
        List<FileInfo> files = new ArrayList<>();
        fileInfoIterable.forEach(files::add);
        return files;
    }

    @Override
    public Optional<FileInfo> findFirstOne(String bizId, String fileGroup) {
        QFileLinkInfo sgcBizFile = QFileLinkInfo.fileBizInfo;
        BooleanExpression expression = sgcBizFile.bizId.eq(bizId);
        Iterable<FileLinkInfo> bizFileIterable = fileLinkInfoRepository.findAll(expression);
        String fileId = null;
        for (FileLinkInfo bizFile : bizFileIterable) {
            fileId = bizFile.getFileId();
            break;
        }
        if (Strings.isNullOrEmpty(fileId)) {
            return Optional.empty();
        }
        return fileInfoRepository.findById(fileId).map(a -> a);
    }

    @Override
    public Optional<FileInfo> findFirstOne(String fileId) {
		return fileInfoRepository.findById(fileId).map(a -> a);
	}

}
