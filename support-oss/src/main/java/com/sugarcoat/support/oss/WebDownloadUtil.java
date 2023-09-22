package com.sugarcoat.support.oss;

import cn.hutool.core.util.URLUtil;
import com.sugarcoat.protocol.exception.ServerException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * web下载工具
 *
 * @author 许向东
 * @since 2023/9/15
 */
public class WebDownloadUtil {

    /**
     * 下载系统内置模板
     *
     * @param response 响应
     * @param templatePath 模板路径
     * @param filename 文件名
     */
    public static void downloadTemplate(HttpServletResponse response, ResourceLoader resourceLoader, String templatePath, String filename){
        org.springframework.core.io.Resource resource = resourceLoader.getResource(templatePath);
        try (InputStream inputStream = resource.getInputStream()) {
            download(response, inputStream, filename);
        } catch (IOException e) {
            throw new ServerException("文件下载失败");
        }
    }

    /**
     * 文件下载
     *
     * @param response 响应
     * @param inputStream 文件流
     * @param filename 文件名
     */
    public static void download(HttpServletResponse response, InputStream inputStream, String filename){
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename*=utf-8''" + URLUtil.encode(filename, StandardCharsets.UTF_8));
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
