package com.sugarcoat.oss.api;

import java.io.InputStream;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/5
 */
public interface FileClient {

    void createBucket();

    UploadResult upload(String path, InputStream inputStream, String contentType);

    InputStream getContent(String fileUrl);

    void delete(String fileUrl);

}