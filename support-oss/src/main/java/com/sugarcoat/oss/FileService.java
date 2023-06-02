package com.sugarcoat.oss;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * 文件对外服务
 *
 * @author xxd
 * @date 2023/6/2
 */
public interface FileService {

    FileInfo upload(FileGroup fileGroup, MultipartFile multipartFile);

    void download(HttpServletResponse response, FileGroup fileGroup, String fileId);

    void remove(FileGroup fileGroup, String fileId);

    void remove(FileGroup fileGroup, Set<String> fileIds);

}