package com.sugarcoat.support.oss.application;

import com.sugarcoat.api.oss.FileBusinessManager;
import com.sugarcoat.api.oss.FileClient;
import com.sugarcoat.api.exception.ServerException;
import com.sugarcoat.api.oss.FileObject;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

/**
 * 文件对外服务
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/2
 */
//@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileClient fileClient;

    private final FileBusinessManager fileBusinessManager;

    private final FileRepository fileRepository;

    @Override
    public SugarcoatFileInfo upload(String fileGroup, MultipartFile multipartFile) {
        String filePath = "/" + fileGroup + "/" + UUID.randomUUID();
        try {
            fileClient.upload(filePath, multipartFile.getInputStream(), multipartFile.getContentType());
            SugarcoatFileInfo fileInfo = new SugarcoatFileInfo();
            String originalFilename = multipartFile.getOriginalFilename();
            fileInfo.setFileGroup(fileGroup);
            fileInfo.setFileName(originalFilename);
            fileInfo.setFilePath(filePath);
            fileInfo.setFileSize(multipartFile.getSize());
            String[] split = originalFilename.split(".");
            //todo 数组越界
            fileInfo.setFileType(split[1]);
            fileRepository.save(fileInfo);
            return fileInfo;
        } catch (IOException e) {
            throw new ServerException("文件上传失败", e);
        }
    }

    @Override
    public void download(HttpServletResponse response, String fileGroup, String fileId) {
        SugarcoatFileInfo fileInfo = fileRepository.findById(fileId).orElseThrow(() -> new ServerException("文件不存在"));
        try (ServletOutputStream outputStream = response.getOutputStream();
             FileObject fileObject = fileClient.getFileObject(fileInfo.getFilePath());
             InputStream inputStream = fileObject.getContent()) {
            long contentLengthLong = fileObject.getContentLength();
            int contentLengthInt = Long.valueOf(contentLengthLong).intValue();
            response.setContentLength(contentLengthInt);
            response.setHeader("Content-Disposition", "attachment;filename=" + fileInfo.getFileName());
            response.setContentType(fileObject.getContentType());

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
        SugarcoatFileInfo fileInfo = fileRepository.findById(fileId).orElseThrow(() -> new ServerException("文件不存在"));
        fileClient.delete(fileInfo.getFilePath());
        fileBusinessManager.separateFileId(fileInfo.getFileId());
    }

    @Override
    public void remove(String fileGroup, Set<String> fileIds) {
        Iterable<SugarcoatFileInfo> fileInfos = fileRepository.findAllById(fileIds);
        for (SugarcoatFileInfo fileInfo : fileInfos) {
            fileClient.delete(fileInfo.getFilePath());
        }
        fileBusinessManager.separateFileId(fileIds);
    }

}
