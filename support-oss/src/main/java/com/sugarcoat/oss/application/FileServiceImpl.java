package com.sugarcoat.oss.application;

import com.amazonaws.services.s3.AmazonS3;
import com.sugarcoat.oss.api.FileGroup;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * 文件对外服务
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/2
 */
@Service
public class FileServiceImpl implements FileService {

    @Override
    public SugarcoatFileInfo upload(FileGroup fileGroup, MultipartFile multipartFile) {
        return null;
    }

    @Override
    public void download(HttpServletResponse response, FileGroup fileGroup, String fileId) {
    }

    @Override
    public void remove(FileGroup fileGroup, String fileId) {

    }

    @Override
    public void remove(FileGroup fileGroup, Set<String> fileIds) {

    }
}
