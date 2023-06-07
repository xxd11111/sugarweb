package com.sugarcoat.oss.application;

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

    SugarcoatFileInfo upload(String fileGroup, MultipartFile multipartFile);

    void download(HttpServletResponse response, String fileGroup, String fileId);

    void remove(String fileGroup, String fileId);

    void remove(String fileGroup, Set<String> fileIds);

}
