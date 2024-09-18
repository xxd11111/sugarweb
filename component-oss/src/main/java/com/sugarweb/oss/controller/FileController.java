package com.sugarweb.oss.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.common.R;
import com.sugarweb.framework.exception.ServerException;
import com.sugarweb.oss.application.FileDto;
import com.sugarweb.oss.application.FileService;
import com.sugarweb.oss.application.dto.FileQuery;
import com.sugarweb.oss.domain.FileInfo;
import com.sugarweb.oss.utils.WebDownloadUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * 文件控制器
 *
 * @author xxd
 * @version 1.0
 */
@RestController
@RequestMapping("file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("download")
    public void download(HttpServletResponse response, String fileId) {
        FileInfo fileInfo = fileService.getFileInfo(fileId);
        if (fileInfo == null) {
            return;
        }
        try (InputStream content = fileService.getContentByKey(fileInfo.getFileKey())) {
            WebDownloadUtil.download(response, content, fileInfo.getFilename(), fileInfo.getContentType());
        } catch (IOException e) {
            throw new ServerException("文件下载失败", e);
        }
    }

    @PostMapping("upload")
    public R<FileDto> upload(MultipartFile multipartFile, String fileGroup) {
        try {
            return R.data(fileService.upload(fileGroup, multipartFile.getInputStream(), multipartFile.getContentType(), multipartFile.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("batchRemove")
    public R<Void> batchRemove(Set<String> fileIds) {
        fileService.batchRemove(fileIds);
        return R.ok();
    }

    @PostMapping("remove")
    public R<Void> remove(String fileId) {
        fileService.remove(fileId);
        return R.ok();
    }

    @PostMapping("page")
    public R<IPage<FileDto>> page(PageQuery pageQuery, FileQuery query) {
        return R.data(fileService.page(pageQuery, query));
    }

}
