package com.sugarweb.oss.application;

import com.sugarweb.framework.exception.ServerException;
import com.sugarweb.oss.domain.FileInfoRepository;
import com.sugarweb.oss.infra.FileClient;
import com.sugarweb.oss.infra.FileUploadResult;
import com.sugarweb.oss.domain.FileInfo;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 文件服务
 *
 * @author xxd
 * @version 1.0
 */
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileClient fileClient;
    private final FileLinkService fileLinkService;
    private final FileInfoRepository fileRepository;

    @Override
    public FileInfo upload(String fileGroup, InputStream inputStream, String contentType, String filename) {
        String key = "/" + fileGroup + "/" + UUID.randomUUID();
        FileUploadResult upload = fileClient.upload(key, inputStream, contentType);
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileGroup(fileGroup);
        fileInfo.setFilename(filename);
        fileInfo.setKey(key);
        fileInfo.setFileSize(upload.getFileSize());
        fileInfo.setFileType(getFileType(filename));
        fileRepository.save(fileInfo);
        return fileInfo;
    }

    //只判断文件名，不识别文件magic值
    private String getFileType(String filename) {
        if (filename == null) {
            return null;
        }
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex == -1) {
            return null;
        }
        return filename.substring(dotIndex, filename.length() - 1);
    }

    @Override
    public InputStream findContent(String fileId) {
        return fileClient.getContent(fileId);
    }

    @Override
    public Optional<FileInfo> findOne(String fileId) {
        return Optional.ofNullable(fileRepository.selectById(fileId));
    }

    @Override
    public void remove(String fileId) {
        FileInfo fileInfo = findOne(fileId).orElseThrow(() -> new ServerException("文件不存在"));
        fileClient.delete(fileInfo.getKey());
        fileLinkService.breakByFileId(fileInfo.getId());
    }

    @Override
    public void remove(Collection<String> fileIds) {
        List<FileInfo> fileInfos = fileRepository.selectBatchIds(fileIds);
        for (FileInfo fileInfo : fileInfos) {
            fileClient.delete(fileInfo.getKey());
        }
        fileLinkService.breakByFileIds(fileIds);
    }

}
