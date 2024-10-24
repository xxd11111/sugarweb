package com.sugarweb.oss.utils;

import cn.hutool.core.io.resource.ResourceUtil;
import com.sugarweb.framework.exception.ServerException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * web下载工具
 *
 * @author xxd
 * @version 1.0
 */
public class WebDownloadUtil {

    /**
     * 下载系统内置模板
     *
     * @param response 响应
     * @param templatePath 模板路径
     * @param filename 文件名
     */
    public static void downloadTemplate(HttpServletResponse response,String templatePath, String filename, String contentType){
        try (InputStream inputStream = ResourceUtil.getStream(templatePath)) {
            download(response, inputStream, filename, contentType);
        } catch (IOException e) {
            throw new ServerException("文件下载失败");
        }
    }

    /**
     * 文件下载
     *
     * @param response    响应
     * @param inputStream 文件流
     * @param filename    文件名
     */
    public static void download(HttpServletResponse response, InputStream inputStream, String filename, String contentType) {
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment;filename*=utf-8''" + URLEncoder.encode(filename, StandardCharsets.UTF_8));
            int len;
            int contentLength = 0;
            byte[] bytes = new byte[1024];
            while ((len = inputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, len);
                contentLength += len;
            }
            response.setContentLength(contentLength);
            outputStream.flush();
        } catch (IOException e) {
            throw new ServerException("文件下载失败");
        }
    }

}
