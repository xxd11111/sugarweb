package com.sugarcoat.oss;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
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
    public FileInfo upload(FileGroup fileGroup, MultipartFile multipartFile) {
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
