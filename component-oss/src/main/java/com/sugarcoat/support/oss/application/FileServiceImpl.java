package com.sugarcoat.support.oss.application;

import com.xxd.exception.ServerException;
import com.sugarcoat.support.oss.api.BizFileManager;
import com.sugarcoat.support.oss.api.FileClient;
import com.sugarcoat.support.oss.api.UploadInfo;
import com.sugarcoat.support.oss.domain.SgcFileInfo;
import com.sugarcoat.support.oss.domain.SgcFileInfoRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

/**
 * 文件对外服务
 *
 * @author xxd
 * @version 1.0
 * @since 2023/6/2
 */
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileClient fileClient;
    private final BizFileManager bizFileManager;

    private final SgcFileInfoRepository fileRepository;

    @Override
    public SgcFileInfo upload(String fileGroup, InputStream inputStream, String contentType, String filename) {
        String key = "/" + fileGroup + "/" + UUID.randomUUID();
        UploadInfo upload = fileClient.upload(key, inputStream, contentType);
        SgcFileInfo fileInfo = new SgcFileInfo();
        fileInfo.setBizType(fileGroup);
        fileInfo.setFilename(filename);
        fileInfo.setKey(key);
        fileInfo.setFileSize(upload.getFileSize());
        fileInfo.setFileType(getFileType(filename));
        fileRepository.save(fileInfo);
        return fileInfo;
    }

    //只判断文件名，不识别文件magic值
    private String getFileType(String filename) {
        if (filename == null){
            return null;
        }
        String[] split = filename.split("\\.");
        if (split.length <= 1) {
            return null;
        }
        return split[1];
    }

    @Override
    public void download(HttpServletResponse response, String fileGroup, String fileId) {
        SgcFileInfo fileInfo = fileRepository.findById(fileId).orElseThrow(() -> new ServerException("文件不存在"));
        try (ServletOutputStream outputStream = response.getOutputStream();
             InputStream inputStream = fileClient.getContent(fileInfo.getKey())) {
            long fileSize = fileInfo.getFileSize();
            response.setContentLengthLong(fileSize);
            response.setHeader("Content-Disposition", "attachment;filename=" + fileInfo.getFilename());
            response.setContentType(fileInfo.getContentType());

            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, len);
            }
            outputStream.flush();
        } catch (IOException e) {
            throw new ServerException("文件下载失败", e);
        }
    }

    @Override
    public void remove(String fileGroup, String fileId) {
        SgcFileInfo fileInfo = fileRepository.findById(fileId).orElseThrow(() -> new ServerException("文件不存在"));
        fileClient.delete(fileInfo.getKey());
        bizFileManager.separateByFileId(fileInfo.getId());
    }

    @Override
    public void remove(String fileGroup, Set<String> fileIds) {
        Iterable<SgcFileInfo> fileInfos = fileRepository.findAllById(fileIds);
        for (SgcFileInfo fileInfo : fileInfos) {
            fileClient.delete(fileInfo.getKey());
        }
        bizFileManager.separateByFileId(fileIds);
    }

}
