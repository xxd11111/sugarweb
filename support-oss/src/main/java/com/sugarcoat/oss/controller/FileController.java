package com.sugarcoat.oss.controller;

import com.sugarcoat.oss.application.FileCmd;
import com.sugarcoat.oss.application.FileService;
import com.sugarcoat.oss.application.SugarcoatFileInfo;
import com.sugarcoat.api.common.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * 文件控制器
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/5
 */
@RestController
@RequestMapping("file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping("download")
    public void download(HttpServletResponse response, FileCmd fileCmd) {
        //ServletRequestAttributes response = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        fileService.download(response, fileCmd.getFileGroup(), fileCmd.getFileId());
    }

    @PostMapping("upload")
    public Result<SugarcoatFileInfo> upload(MultipartFile multipartFile, String fileGroup) {
        return Result.data(fileService.upload(fileGroup, multipartFile));
    }

    @PostMapping("batchRemove")
    public Result<Void> remove(String fileGroup, Set<String> fileIds) {
        fileService.remove(fileGroup, fileIds);
        return Result.ok();
    }

    @PostMapping("remove")
    public Result<Void> remove(String fileGroup, String fileId) {
        fileService.remove(fileGroup, fileId);
        return Result.ok();
    }
}
