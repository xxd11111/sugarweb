package com.sugarcoat.support.oss.application;

import com.sugarcoat.api.server.exception.ServerException;
import com.sugarcoat.api.oss.BizFileManager;
import com.sugarcoat.api.oss.FileManager;
import com.sugarcoat.support.oss.domain.SgcFileInfo;
import com.sugarcoat.support.oss.domain.SgcFileInfoRepository;
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
 * @since 2023/6/2
 */
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileManager fileManager;
    private final BizFileManager bizFileManager;

    private final SgcFileInfoRepository fileRepository;

    @Override
    public SgcFileInfo upload(String fileGroup, MultipartFile multipartFile) {
        String key = "/" + fileGroup + "/" + UUID.randomUUID();
        try {
            fileManager.upload(key, multipartFile.getInputStream(), multipartFile.getContentType());
            SgcFileInfo fileInfo = new SgcFileInfo();
            String originalFilename = multipartFile.getOriginalFilename();
            fileInfo.setFileGroup(fileGroup);
            fileInfo.setFileName(originalFilename);
            fileInfo.setKey(key);
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
        SgcFileInfo fileInfo = fileRepository.findById(fileId).orElseThrow(() -> new ServerException("文件不存在"));
        try (ServletOutputStream outputStream = response.getOutputStream();
             InputStream inputStream = fileManager.getContent(fileInfo.getKey())) {
            long fileSize = fileInfo.getFileSize();
            response.setContentLengthLong(fileSize);
            response.setHeader("Content-Disposition", "attachment;filename=" + fileInfo.getFileName());
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
        fileManager.delete(fileInfo.getKey());
        bizFileManager.separateByFileId(fileInfo.getId());
    }

    @Override
    public void remove(String fileGroup, Set<String> fileIds) {
        Iterable<SgcFileInfo> fileInfos = fileRepository.findAllById(fileIds);
        for (SgcFileInfo fileInfo : fileInfos) {
            fileManager.delete(fileInfo.getKey());
        }
        bizFileManager.separateByFileId(fileIds);
    }

}
