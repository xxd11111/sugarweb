package com.sugarcoat.api.oss;

import java.io.InputStream;

/**
 * 文件客户端
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/5
 */
public interface FileClient {

	/**
	 * 创建
	 */
	void createBucket();

	void upload(String path, InputStream inputStream, String contentType);

	InputStream getContent(String filePath);

	FileObject getFileObject(String filePath);

	//todo 批量处理
	void delete(String filePath);

}