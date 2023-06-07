package com.sugarcoat.oss.application;

import cn.hutool.core.util.IdUtil;
import com.sugarcoat.oss.api.FileClient;
import com.sugarcoat.protocol.exception.ServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * 文件对外服务
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/2
 */
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileClient fileClient;
    private final FileRepository fileRepository;

    @Override
    public SugarcoatFileInfo upload(String fileGroup, MultipartFile multipartFile) {
        String filePath = "/" + fileGroup + "/" + IdUtil.simpleUUID();
        try {
            fileClient.upload(filePath, multipartFile.getInputStream(), multipartFile.getContentType());
            SugarcoatFileInfo fileInfo = new SugarcoatFileInfo();
            fileRepository.save(fileInfo);
            return fileInfo;
        } catch (IOException e) {
            throw new ServerException("文件上传失败", e);
        }finally {
            //todo
            //删除 上传文件
            //删除 fileClient文件
        }
    }

    @Override
    public void download(HttpServletResponse response, String fileGroup, String fileId) {
        SugarcoatFileInfo fileInfo = fileRepository.findById(fileId).orElseThrow(() -> new ServerException("文件不存在"));
        InputStream content = fileClient.getContent(fileInfo.getFileUrl());
        //todo
    }

    @Override
    public void remove(String fileGroup, String fileId) {
        SugarcoatFileInfo fileInfo = fileRepository.findById(fileId).orElseThrow(() -> new ServerException("文件不存在"));
        fileClient.delete(fileInfo.getFileUrl());
    }

    @Override
    public void remove(String fileGroup, Set<String> fileIds) {
        Iterable<SugarcoatFileInfo> fileInfos = fileRepository.findAllById(fileIds);
        for (SugarcoatFileInfo fileInfo : fileInfos) {
            fileClient.delete(fileInfo.getFileUrl());
        }
    }
}
