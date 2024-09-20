package com.sugarweb.oss.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.common.R;
import com.sugarweb.framework.exception.ServerException;
import com.sugarweb.framework.exception.ServiceException;
import com.sugarweb.oss.application.FileDto;
import com.sugarweb.oss.application.FileService;
import com.sugarweb.oss.application.dto.FileQuery;
import com.sugarweb.oss.domain.FileInfo;
import com.sugarweb.oss.utils.WebDownloadUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
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
@Tag(name = "文件管理")
@Slf4j
public class FileController {

    private final FileService fileService;

    @GetMapping("download")
    @Operation(operationId = "file:download", summary = "下载")
    public void download(HttpServletResponse response, @RequestParam String fileId) {
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
    @Operation(operationId = "file:upload", summary = "上传")
    public R<FileDto> upload(MultipartFile multipartFile, String fileGroup) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            FileDto upload = fileService.upload(fileGroup, inputStream, multipartFile.getContentType(), multipartFile.getOriginalFilename());
            return R.data(upload);
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new ServiceException("文件上传失败");
        }
    }

    @PostMapping("batchRemove")
    @Operation(operationId = "file:batchRemove", summary = "批量删除")
    public R<Void> batchRemove(Set<String> fileIds) {
        fileService.batchRemove(fileIds);
        return R.ok();
    }

    @PostMapping("remove")
    @Operation(operationId = "file:remove", summary = "删除")
    public R<Void> remove(String fileId) {
        fileService.remove(fileId);
        return R.ok();
    }

    @PostMapping("page")
    @Operation(operationId = "file:page", summary = "分页")
    public R<IPage<FileDto>> page(PageQuery pageQuery, FileQuery query) {
        return R.data(fileService.page(pageQuery, query));
    }

}
