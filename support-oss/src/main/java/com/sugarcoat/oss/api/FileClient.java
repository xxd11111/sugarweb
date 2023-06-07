package com.sugarcoat.oss.api;

import java.io.InputStream;

/**
 * 文件客户端
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/5
 */
public interface FileClient {

    void createBucket();

    void upload(String path, InputStream inputStream, String contentType);

    InputStream getContent(String fileUrl);

    void delete(String fileUrl);

}