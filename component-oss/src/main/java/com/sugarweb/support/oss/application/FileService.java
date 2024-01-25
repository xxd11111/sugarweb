package com.sugarweb.support.oss.application;

import com.sugarweb.support.oss.domain.FileInfo;
import jakarta.servlet.http.HttpServletResponse;

import java.io.InputStream;
import java.util.Set;

/**
 * 文件对外服务
 *
 * @author xxd
 * @version 1.0
 */
public interface FileService {

	FileInfo upload(String fileGroup, InputStream inputStream, String contentType, String filename);

	void download(HttpServletResponse response, String fileGroup, String fileId);

	void remove(String fileGroup, String fileId);

	void remove(String fileGroup, Set<String> fileIds);

}
